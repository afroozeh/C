package org.iguana;

import java.io.File;
import java.util.List;

import org.iguana.grammar.Grammar;
import org.iguana.grammar.symbol.Character;
import org.iguana.grammar.symbol.Epsilon;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Start;
import org.iguana.grammar.transformation.EBNFToBNF;
import org.iguana.grammar.transformation.LayoutWeaver;
import org.iguana.parser.ParseResult;
import org.iguana.util.IguanaRunner;
import org.iguana.util.RunResult;
import org.iguana.util.RunResults;

public class Java {
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/JavaSpecificationContextAware"));
	private static Grammar grammar = new LayoutWeaver().transform(new EBNFToBNF().transform(originalGrammar));
	private static Start start = grammar.getStartSymbol(Nonterminal.withName("CompilationUnit"));
	
	private static String sourceDir = "/Users/aliafroozeh/corpus/Java/slf4j-1.7.12";
	
	public static void main(String[] args) {
//		List<ParseResult> results = IguanaRunner.builder(grammar, Nonterminal.withName("StatementExpression")).addString("logger.info(\"Activator.start()\")").build().run();
		
//		System.out.println(originalGrammar.getNonterminals().size());
//		List<RunResult> results = IguanaRunner.builder(grammar, start)
//				                              .addFile("files/AMD64RawAssembler.java")
//				                              .setWarmupCount(3)
//				                              .setRunCount(5)
//				                              .setRunGCInBetween(true)
//				                              .build().run();

//		System.out.println(results);
		List<RunResult> results = IguanaRunner.builder(grammar, start)
				                                .setWarmupCount(3)
				                                .setRunCount(5)
				                                .setRunGCInBetween(true)
				                                .setLimit(20)
				                                .addDirectory(sourceDir, "java", true)
				                                .build()
				                                .run();
		
//		System.out.println(results);
		
		System.out.println(RunResults.format(results));

		System.out.println(RunResults.format(RunResults.groupByInput(results)));
		
//		IguanaRunner.builder(grammar, Nonterminal.withName("DecimalIntegerLiteral")).addString("84").build().run();
	}

}
