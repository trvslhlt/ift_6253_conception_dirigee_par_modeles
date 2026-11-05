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
	
	// trailing empty newline intentional
	def generate(Paragraph it) '''
		«text.escape»
		
	'''
	
	// escape special chars, one at a time
	def escape(String s) {
		val out = new StringBuilder
		for (c : s.toCharArray.map[toString]) {
			out.append(switch c {
				case '\\': '\\textbackslash{}' // \\ would be a line break
				case '~': '\\textasciitilde{}' // \~ is an accent
				case '^': '\\textasciicircum{}' // \^ is an accent
				case '_&%$#{}'.contains(c): '\\' + c // a backslash is enough
				default: c // not special
			})
		}
		out.toString
	}
}

