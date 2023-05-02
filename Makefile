.PHONY: up
up:
	docker compose up -d

.PHONY: stop
stop:
	docker compose stop

.PHONY: clean
clean:
	./bin/clean.sh

.PHONY: build
build:
	docker compose build
