package org.iguana;

import java.io.File;

import org.iguana.datadependent.util.IguanaRunner;
import org.iguana.grammar.Grammar;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Start;
import org.iguana.grammar.transformation.EBNFToBNF;
import org.iguana.grammar.transformation.LayoutWeaver;

public class Main {
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/C"));
	private static Grammar grammar = new LayoutWeaver().transform(new EBNFToBNF().transform(originalGrammar));
	private static Start start = Start.from(Nonterminal.withName("TranslationUnit"));
	
	public static void main(String[] args) {
		IguanaRunner.builder(grammar, start).addFile("files/Test.c").build().run();
	}

}
