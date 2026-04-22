# HMCTS Dev Test Backend

### Start the app

`./gradlew bootRun`

### Test the app

`./gradlew check` (runs unit, integration, and functional tests)

### Interact with the app

The API runs on `http://localhost:8080` by default.

Available task statuses:
- `IN_PROGRESS`
- `COMPLETED`

Get all tasks:

```bash
curl -X GET http://localhost:8080/tasks
```

Get a single task by id:

```bash
curl -X GET http://localhost:8080/tasks/1
```

Create a task:

```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Prepare hearing bundle",
    "description": "Collect and verify documents",
    "status": "IN_PROGRESS",
    "dueDateTime": "2026-05-01T10:30:00"
  }'
```

Update task status:

```bash
curl -X PATCH http://localhost:8080/tasks/1/status \
  -H "Content-Type: application/json" \
  -d '{
    "status": "COMPLETED"
  }'
```

Delete a task:

```bash
curl -X DELETE http://localhost:8080/tasks/1
```
