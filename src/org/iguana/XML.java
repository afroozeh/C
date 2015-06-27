package org.iguana;

import java.io.File;

import org.iguana.datadependent.util.IguanaRunner;
import org.iguana.grammar.Grammar;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Start;
import org.iguana.grammar.transformation.EBNFToBNF;

public class XML {
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/XML"));
	private static Grammar grammar = new EBNFToBNF().transform(originalGrammar);
	private static Start start = Start.from(Nonterminal.withName("Document"));
	
	public static void main(String[] args) {
		IguanaRunner.builder(grammar, start).addFile("files/Test.cs").build().run();
	}

}
