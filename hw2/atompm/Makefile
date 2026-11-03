# AToMPM with all user state (models, formalisms, icons) stored in ./users.
# Requires the upstream atompmvm:latest image (see Dockerfile).

IMAGE     := atompm-hw1
CONTAINER := atompm-hw1
PORT      := 8124
USERS     := $(CURDIR)/users

.DEFAULT_GOAL := help
.PHONY: help build seed run stop reset

help: ## List targets
	@grep -E '^[a-z]+:.*##' $(MAKEFILE_LIST) | awk -F':.*## ' '{printf "  make %-8s %s\n", $$1, $$2}'

build: ## Build the atompm-hw1 image
	docker build -q -t $(IMAGE) .

# A bind mount of an empty folder hides the image's users/(default) template and
# breaks account creation, so seed the folder from the image first.
seed: build ## Seed ./users from the image (no-op if already seeded)
	@if [ ! -d "$(USERS)/(default)" ]; then \
		mkdir -p "$(USERS)" && \
		docker create --name atompm-seed $(IMAGE) >/dev/null && \
		docker cp atompm-seed:/opt/atompm/users/. "$(USERS)" && \
		docker rm atompm-seed >/dev/null; \
	fi

run: seed ## Run AToMPM at http://localhost:8124/atompm (Ctrl+C to stop)
	docker run --rm --name $(CONTAINER) -p $(PORT):8124 -v "$(USERS):/opt/atompm/users" $(IMAGE)

stop: ## Stop the container if running in another terminal
	-docker stop $(CONTAINER)

reset: ## DELETE ./users (all your AToMPM work) so it reseeds next run
	@printf 'Delete %s and all models in it? [y/N] ' "$(USERS)"; read ans; \
	[ "$$ans" = y ] && rm -rf "$(USERS)" || echo aborted
