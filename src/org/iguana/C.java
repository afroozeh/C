package org.iguana;

import java.io.File;
import java.util.List;

import org.iguana.grammar.Grammar;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Start;
import org.iguana.grammar.transformation.EBNFToBNF;
import org.iguana.grammar.transformation.LayoutWeaver;
import org.iguana.util.IguanaRunner;
import org.iguana.util.RunResult;
import org.iguana.util.RunResults;

public class C {
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/CContextAware"));
	private static Grammar grammar = new LayoutWeaver().transform(new EBNFToBNF().transform(originalGrammar));
	private static Start start = grammar.getStartSymbol(Nonterminal.withName("TranslationUnit"));
	
	public static void main(String[] args) {
		List<RunResult> results = IguanaRunner.builder(grammar, start).addFile("files/Test.c").build().run();
		System.out.println(RunResults.format(results));
	}

}
