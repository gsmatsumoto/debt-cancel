output "vpc_id" {
  value = module.vpc.vpc_id
}

output "eks_cluster_id" {
  value = module.eks.cluster_id
}

output "eks_cluster_endpoint" {
  value = module.eks.cluster_endpoint
}

output "sqs_queue_url" {
  value = aws_sqs_queue.cancel_debt_fifo.id
}

output "sqs_publisher_role_arn" {
  value = aws_iam_role.sqs_publisher_role.arn
}