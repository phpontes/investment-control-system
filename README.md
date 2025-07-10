
# Investment Control System

Projeto desenvolvido com Java + Spring Boot para controle de investimentos e operações em ativos da B3, inspirado em um desafio técnico real proposto pelo Itaú.

> ⚠️ Projeto em desenvolvimento — atualmente pausado para reestruturação e revisão de fundamentos.

---

## 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- MySQL
- Apache Kafka
- Maven
- Lombok
- Postman
- Kafka (modo local, sem Zookeeper)
- API pública da B3 (`https://b3api.vercel.app/`)

---

## 🎯 Funcionalidades

- CRUD de usuários, ativos e operações
- Registro de compras e vendas
- Cálculo de posições (P&L, preço médio, quantidade)
- Produção e consumo de cotações via Kafka (em andamento)
- Integração com a API pública da B3 (em avaliação)

---

## 📦 Estrutura do projeto

```
src/
├── model          → Entidades JPA
├── repository     → Interfaces com Spring Data
├── service        → Regras de negócio
├── controller     → APIs REST
├── kafka          → Producer e Consumer Kafka
├── scheduler      → Tarefas automáticas (ex: consulta à API da B3)
└── dto            → Objetos de transferência (ex: CotacaoKafkaDTO)
```

---

## ⚙️ Banco de dados

Utiliza **MySQL** com auto-criação de tabelas (`spring.jpa.hibernate.ddl-auto=update`).

Estrutura do banco:
- `usuarios`
- `ativos`
- `operacoes`
- `cotacoes`
- `posicoes`

---

## 🛰️ Kafka

Configurado localmente via porta `9092`.  
O sistema envia cotações para o tópico `cotacoes` e consome automaticamente para salvar no banco.

---

## 🚧 Status do projeto

🟡 **Em desenvolvimento parcial**  
🛑 Integração Kafka + API externa pausada para estudos posteriores

---

## 👨‍💻 Desenvolvedor

Paulo — Estudante de Java  
Projeto criado com foco em aprendizado prático para portfólio.

---

## 📌 Observação

Este projeto foi inspirado em um desafio técnico real, adaptado para a stack Java + Spring.  
As funcionalidades foram desenvolvidas com base em boas práticas e arquitetura em camadas, buscando sempre a escalabilidade e a clareza do código.
