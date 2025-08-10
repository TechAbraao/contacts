# ============================
# Configurações
# ============================
COMPOSE_DIR := compose
COMPOSE_FILE := $(COMPOSE_DIR)/docker-compose.yml
ENV_FILE := $(COMPOSE_DIR)/.env

# Nome do projeto Docker Compose (para isolar containers)
PROJECT_NAME := contacts-api

# ============================
# Comandos do Docker Compose
# ============================
up:
	@echo "🚀 Subindo containers..."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) up -d

down:
	@echo "🛑 Derrubando containers..."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) down

restart: down up

logs:
	@echo "📜 Logs dos serviços..."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) logs -f

ps:
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) ps

build:
	@echo "🏗️ Buildando imagens..."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) build

# ============================
# Limpeza
# ============================
clean:
	@echo "🧹 Limpando containers, volumes e imagens órfãs..."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) down -v --remove-orphans
	docker system prune -f

# ============================
# Ajuda
# ============================
help:
	@echo "Comandos disponíveis:"
	@echo "  make up        -> Sobe os containers"
	@echo "  make down      -> Derruba os containers"
	@echo "  make restart   -> Reinicia os containers"
	@echo "  make logs      -> Mostra logs em tempo real"
	@echo "  make ps        -> Lista containers ativos"
	@echo "  make build     -> Faz o build das imagens"
	@echo "  make clean     -> Remove tudo (containers, volumes, imagens órfãs)"
