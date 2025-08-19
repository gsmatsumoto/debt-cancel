resource "aws_sqs_queue" "cancel_debt_fifo" {
  name                       = "${var.project_name}.fifo"
  fifo_queue                 = true
  content_based_deduplication = true
}

# IAM Role for ServiceAccount (IRSA)
resource "aws_iam_role" "sqs_publisher_role" {
  name = "${var.project_name}-sqs-prod"

  assume_role_policy = data.aws_iam_policy_document.irsa_assume_role.json
}

data "aws_iam_policy_document" "irsa_assume_role" {
  statement {
    actions = ["sts:AssumeRoleWithWebIdentity"]
    effect  = "Allow"
    principals {
      identifiers = [module.eks.oidc_provider_arn]
      type        = "Federated"
    }
    condition {
      test     = "StringEquals"
      variable = "eks_oidc_provider_url:sub"
      values   = ["system:serviceaccount:default:sqs-sa"]
    }
  }
}

resource "aws_iam_policy" "sqs_policy" {
  name = "${var.project_name}-sqs-policy"
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect   = "Allow",
        Action   = ["sqs:SendMessage"],
        Resource = aws_sqs_queue.cancel_debt_fifo.arn
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "attach_sqs_policy" {
  role       = aws_iam_role.sqs_publisher_role.name
  policy_arn = aws_iam_policy.sqs_policy.arn
}