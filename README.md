# 🧾 Serviço de Cancelamento de Débito

Este projeto é uma aplicação Java 21 construída com arquitetura **Hexagonal (Ports & Adapters)**. O serviço expõe um endpoint HTTP para receber requisições de cancelamento de débito via **API Gateway** e publica eventos de cancelamento em uma fila **AWS SQS**.

A infraestrutura é provisionada utilizando **Terraform**.

---

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3**
- **Arquitetura Hexagonal**
- **Docker**
- **Kubernetes (EKS)**
- **AWS SQS**
- **Terraform**
- **AWS Load Balancer Controller / NGINX Ingress**
- **AWS SDK for Java (v2)**

---

## 🧱 Arquitetura Hexagonal

A aplicação é estruturada nos seguintes módulos:


              ┌─────────────────────┐
              │     Controller      │  ← adapter.in.web (REST API)
              └─────────┬───────────┘
                        │
                        ▼
              ┌─────────────────────┐
              │    Use Cases /      │  ← application.service
              │ Business Logic      │
              └─────────┬───────────┘
                        │
                        ▼
              ┌─────────────────────┐
              │  Event Publisher    │  ← adapter.out.sqs
              │  (SQS FIFO Client)  │
              └─────────┬───────────┘
                        │
                        ▼
              ┌─────────────────────┐
              │     AWS SQS FIFO    │  ← Fila de mensagens externa
              └─────────────────────┘

---

## 📌 Funcionalidade

- Exposição de endpoint REST `/debt/cancel`
- Recebimento de requisição via API Gateway
- Cancelamento de débito (simulado)
- Publicação de evento em uma fila SQS
- Resposta `200 OK` com mensagem de confirmação

---

## 🔄 Fluxo da Requisição

1. Cliente faz `POST` para o endpoint `/debt/cancel`
2. Controlador REST (adapter de entrada) recebe a requisição
3. Serviço da aplicação executa a lógica de cancelamento
4. Adapter de saída envia mensagem para o SQS
5. Resposta é retornada ao cliente com status `200 OK`

### Payload Request

```json
{
  "debtId": "123456789"
} 
```

### Payload Response
```json
{
  "status": 200,
  "message": "Débito cancelado com sucesso."
}
```