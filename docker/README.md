# Docker Environment - Contacts REST API

This directory contains the **Docker Compose** configuration for running the required services for the **Contacts REST API** project.

## Structure

```bash
compose/
├── docker-compose.yml # Services definition (PostgreSQL + PgAdmin)
├── .env # Environment variables for the services
└── README.md # This file
```

## Services

The `docker-compose.yml` currently starts the following services:

- **PostgreSQL** (`postgres`)
  - Application database
  - Configured using variables defined in `.env`

- **PgAdmin** (`pgadmin`)
  - GUI tool to manage PostgreSQL
  - Credentials defined in `.env`

## Environment Variables

The `.env` file in this directory contains all variables needed to start the services.

Example:
```env
## DATABASE POSTGRESQL ##
DATABASE_HOST=localhost
DATABASE_PORT=5432
DATABASE_NAME=contacts
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=contacts

## PGADMIN ##
PGADMIN_DEFAULT_EMAIL=admin@admin.com
PGADMIN_DEFAULT_PASSWORD=admin
PGADMIN_HOST=5050
PGADMIN_PORT=80
```

> **Important:** This .env is only for Docker Compose.
> Application environment variables may be defined in a different .env file at the project root.

Starting the Services
This project uses a Makefile at the root directory to simplify Docker Compose commands.

From the project root, run ``make`` to see the available commands:
````bash
Available commands:
  make up        -> Start containers
  make down      -> Stop and remove containers
  make stop      -> Stop containers (keep them)
  make start     -> Start stopped containers
  make restart   -> Restart containers (down + up)
  make logs      -> Show real-time logs
  make ps        -> List active containers
  make build     -> Build images
  make clean     -> Remove containers, volumes, and orphan images
````
🌐 Access
After running make up:
- PostgreSQL: localhost:${DATABASE_PORT}
- PgAdmin: http://localhost:${PGADMIN_HOST}
- Login using the credentials from .env

## Cleaning the Environment
To remove containers, volumes, and orphan images:
````bash
make clean

````