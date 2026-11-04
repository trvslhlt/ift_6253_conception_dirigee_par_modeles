package ift6253.hw2.latex.generator

import ift6253.hw2.latex.Document
import ift6253.hw2.latex.Paragraph
import ift6253.hw2.latex.Section
import ift6253.hw2.latex.SubSection
import ift6253.hw2.latex.SubSubSection


class LaTeXTemplate {

	def generate(Document it) '''
		\documentclass{article}
		«FOR p : packages»
		\usepackage{«p»}
		«ENDFOR»
		\begin{document}
		\title{«title.escape»}
		\author{«(author ?: '').escape»}
		\maketitle
		«FOR s : sections»
		«s.generate»
		«ENDFOR»
		\end{document}
	'''
	
	def generate(Section it) '''
		\section{«name.escape»}
		«FOR ss : subsections»
		«ss.generate»
		«ENDFOR»
	'''
	
	def generate(SubSection it) '''
		\subsection{«name.escape»}
		«FOR sss : subsubsections»
		«sss.generate»
		«ENDFOR»
	'''
	
	def generate(SubSubSection it) '''
		\subsubsection{«name.escape»}
		«FOR p : paragraphs»
		«p.generate»
		«ENDFOR»
	'''
	
	def generate(Paragraph it) '''
		«text.escape»
		
	'''
	
	def escape(String s) {
		s.replace('\\', '\\textbackslash{}')
		 .replace('~', '\\textasciitilde{}')
		 .replace('^', '\\textasciicircum{}')
		 .replaceAll('([_&%$#{}])', '\\\\$1')
	}
}

