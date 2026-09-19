# Odisseia — build e execução
#
#   make up     → recompila sempre e inicia o jogo (versão atual)
#   make run    → recompila só se o código mudou e inicia
#   make build  → só compila
#   make clean  → limpa artefatos

JAVA_RELEASE ?= 21
SRC_DIR      := src
RES_DIR      := resources
OUT_DIR      := target/classes
MAIN_CLASS   := main.Window

# Fontes .java (sem espaços nos paths do projeto)
SOURCES := $(shell find $(SRC_DIR) -name '*.java' | sort)

.PHONY: up run build rebuild clean help sync-resources run-only

help:
	@echo "Alvos:"
	@echo "  make up      Recompila do zero e executa o jogo"
	@echo "  make run     Compila se necessário e executa"
	@echo "  make build   Apenas compila"
	@echo "  make clean   Remove target/"

## Sempre gera a versão nova e executa.
up: rebuild run-only

## Compila apenas quando fontes mudaram e executa.
run: build run-only

run-only:
	@test -f "$(OUT_DIR)/$(subst .,/,$(MAIN_CLASS)).class" || $(MAKE) --no-print-directory build
	@echo ">> Iniciando $(MAIN_CLASS) ..."
	java -cp "$(OUT_DIR)" $(MAIN_CLASS)

rebuild:
	@rm -rf "$(OUT_DIR)"
	@$(MAKE) --no-print-directory build

build: $(OUT_DIR)/.built

$(OUT_DIR)/.built: $(SOURCES)
	@mkdir -p "$(OUT_DIR)"
	@echo ">> Compilando com JDK $(JAVA_RELEASE) ..."
	javac --release $(JAVA_RELEASE) -d "$(OUT_DIR)" $(SOURCES)
	@$(MAKE) --no-print-directory sync-resources
	@touch "$@"

sync-resources:
	@mkdir -p "$(OUT_DIR)"
	@cp -a "$(RES_DIR)/." "$(OUT_DIR)/"
	@echo ">> Recursos sincronizados."

clean:
	rm -rf target
	@echo ">> Limpo."
