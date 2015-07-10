package org.iguana;

import java.io.File;

import org.iguana.grammar.Grammar;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Start;
import org.iguana.grammar.transformation.EBNFToBNF;
import org.iguana.grammar.transformation.LayoutWeaver;
import org.iguana.util.IguanaRunner;

public class C {
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/C"));
	private static Grammar grammar = new LayoutWeaver().transform(new EBNFToBNF().transform(originalGrammar));
	private static Start start = Start.from(Nonterminal.withName("TranslationUnit"));
	
	public static void main(String[] args) {
		IguanaRunner.builder(grammar, start).addFile("files/Test.c").build().run();
	}

}
