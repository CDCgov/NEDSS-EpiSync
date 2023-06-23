.DEFAULT_GOAL := all
black = black --target-version py39 episync
isort = isort --profile black episync

.PHONY: depends
depends:
	@echo "Install dependencies here...."

.PHONY: init
init: depends
	echo "Setting up virtual environment in venv/"
	python3 -m venv venv
	echo "Virtual environment complete."
	echo "Downloading EpiSync MMG DD"
	wget -O ~/episync-dd.xlsx https://raw.githubusercontent.com/CDCgov/NEDSS-EpiSync/main/mmg/episync-dd.xlsx
	echo "Copying PHINVADS Race spreadsheet to home directory"
	cp ../docs/2022_RaceAndEthnicityFinal_TablesforPub_Final.xlsx ~

.PHONY: format
format:
	$(isort)
	$(black)

.PHONY: lint
lint:
	mypy --show-error-codes  --check-untyped-defs --ignore-missing-imports episync
	flake8 --ignore=E203,F841,E501,E722,W503 episync
	$(isort) --check-only --df
	$(black) --check --diff

.PHONY: setup
setup:
	python3 setup.py install

.PHONY: install
install: depends init
	. venv/bin/activate && ( \
	pip3 --no-cache-dir  install -r requirements.txt; \
	yes|mypy --install-types; \
	python3 setup.py install; \
	python3 setup.py clean; \
	)

.PHONY: update
update: format lint
	pip3 freeze | grep -v episync > requirements.txt
	git add setup.py docs bin episync requirements.txt Makefile README.md scripts
	git commit --allow-empty -m "Updates"
	git push origin main
	python3 setup.py install
	git status

.PHONY: build
build:
	docker compose build

.PHONY: up
up: build
	docker compose up

.PHONY: daemon
daemon: build
	docker compose up -d

.PHONY: down
down:
	docker compose stop

.PHONY: docs
docs:
	cd docs
	make -C docs html

.PHONY: clean
clean:
	python3 setup.py clean
	git status

.PHONY: test
test: format lint
	pytest episync/tests

.PHONY: all
all: format lint update docs install test clean run
	git status
