all: main.pdf

main.pdf: main.tex references.bib $(wildcard figures/*)
	latexmk -pdf -interaction=nonstopmode main.tex

clean:
	latexmk -C

.PHONY: all clean
