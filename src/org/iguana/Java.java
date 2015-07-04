package org.iguana;

import java.io.File;
import java.util.List;

import org.iguana.datadependent.util.IguanaRunner;
import org.iguana.grammar.Grammar;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Start;
import org.iguana.grammar.transformation.EBNFToBNF;
import org.iguana.grammar.transformation.LayoutWeaver;
import org.iguana.parser.ParseResult;
import org.iguana.parser.ParseResults;

public class Java {
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/JavaSpecificationContextAware"));
	private static Grammar grammar = new LayoutWeaver().transform(new EBNFToBNF().transform(originalGrammar));
	private static Start start = Start.from(Nonterminal.withName("CompilationUnit"));
	
	private static String sourceDir = "/Users/aliafroozeh/corpus/Java/slf4j-1.7.12";
	
	public static void main(String[] args) {
//		System.out.println(originalGrammar.getNonterminals().size());
//		List<ParseResult> results = IguanaRunner.builder(grammar, start).addFile("files/Test.java").build().run();
//		System.out.println(results);
		List<ParseResult> results = IguanaRunner.builder(grammar, start)
				                                .setWarmupCount(3)
				                                .setRunCount(5)
				                                .setRunGCInBetween(true)
				                                .setLimit(20)
				                                .addDirectory(sourceDir, "java", true)
				                                .build()
				                                .run();
		
//		System.out.println(results);

		System.out.println(ParseResults.format(ParseResults.groupByInput(results)));
		
//		IguanaRunner.builder(grammar, Nonterminal.withName("DecimalIntegerLiteral")).addString("84").build().run();
	}

}
