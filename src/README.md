# Todo Summary Assistant

A full-stack application with a Spring Boot backend and React frontend, allowing users to manage to-dos, store them in Supabase, summarize them using Cohere, and send summaries to Slack.

## Project Structure
- `backend/`: Spring Boot backend (REST API, Supabase, Cohere, Slack).
- `frontend/`: React frontend (UI, API calls via axios).

## Prerequisites
- **Java 17+** and **Maven** (for backend).
- **Node.js 16+** and **npm** (for frontend).
- **PostgreSQL client** (e.g., `psql`) or Supabase account.
- **Git** installed.
- **Supabase project** with a `todos` table.
- **Cohere API key** ([cohere.ai](https://cohere.ai)).
- **Slack webhook URL** ([api.slack.com](https://api.slack.com)).

## Setup

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/todo-summary-assistant-project.git
cd todo-summary-assistant-project