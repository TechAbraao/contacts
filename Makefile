COMPOSE_DIR := compose
COMPOSE_FILE := ./docker/$(COMPOSE_DIR)/docker-compose.yml
ENV_FILE := .env
PROJECT_NAME := contacts

up:
	@echo "Iniciando containers."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) up -d

down:
	@echo "Parando e removendo containers."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) down

stop:
	@echo "Parando containers (sem remover)."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) stop

start:
	@echo "Iniciando containers parados."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) start

restart: down up

logs:
	@echo "Mostrando logs do serviço."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) logs -f

ps:
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) ps

build:
	@echo "Construindo imagens."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) build

clean:
	@echo "Limpando containers, volumes e imagens órfãs."
	docker compose -p $(PROJECT_NAME) --env-file $(ENV_FILE) -f $(COMPOSE_FILE) down -v --remove-orphans
	docker system prune -f

help:
	@echo ""
	@echo "  Comandos disponíveis:"
	@echo ""
	@echo "  make up        -> Iniciar containers."
	@echo "  make down      -> Parar e remover containers."
	@echo "  make stop      -> Parar containers (mantê-los)."
	@echo "  make start     -> Iniciar containers parados."
	@echo "  make restart   -> Reiniciar containers (down + up)."
	@echo "  make logs      -> Mostrar logs em tempo real."
	@echo "  make ps        -> Listar containers ativos."
	@echo "  make build     -> Construir imagens."
	@echo "  make clean     -> Remover containers, volumes e imagens órfãs."
	@echo ""

.DEFAULT_GOAL := help