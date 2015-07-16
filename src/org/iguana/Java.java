package org.iguana;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.iguana.grammar.Grammar;
import org.iguana.grammar.precedence.OperatorPrecedence;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Start;
import org.iguana.grammar.transformation.DesugarPrecedenceAndAssociativity;
import org.iguana.grammar.transformation.EBNFToBNF;
import org.iguana.grammar.transformation.LayoutWeaver;
import org.iguana.parser.ParseResult;
import org.iguana.util.IguanaRunner;
import org.iguana.util.RunResult;
import org.iguana.util.RunResults;
import org.iguana.util.SuccessResult;

public class Java {
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/JavaNaturalContextAware"));
	private static Grammar grammar = new LayoutWeaver().transform(new DesugarPrecedenceAndAssociativity().transform(new EBNFToBNF().transform(originalGrammar)));
//	private static Grammar grammar = new LayoutWeaver().transform(new EBNFToBNF().transform(originalGrammar));
//	private static Grammar grammar = new LayoutWeaver().transform(new OperatorPrecedence(originalGrammar.getPrecedencePatterns(), originalGrammar.getExceptPatterns()).transform(new EBNFToBNF().transform(originalGrammar)));
	
	private static Start start = grammar.getStartSymbol(Nonterminal.withName("CompilationUnit"));
	
	private static String slf4j = "/Users/aliafroozeh/corpus/Java/slf4j-1.7.12";
	private static String junit = "/Users/aliafroozeh/corpus/Java/junit-r4.12";
	private static String jdk1_7 = "/Users/aliafroozeh/corpus/Java/jdk1.7.0_60-b19";
	
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
		
//		System.out.println(grammar);
		
		List<RunResult> results = IguanaRunner.builder(grammar, start)
				                              .setWarmupCount(3)
				                              .setRunCount(5)
//				                              .setRunGCInBetween(false)
//				                              .setLimit(20)
				                              .addDirectory(jdk1_7, "java", true)
				                              .addDirectory(slf4j, "java", true)
				                              .addDirectory(junit, "java", true)
//				                              .addFile("files/Test.java")
				                              .build()
				                              .run();
		
		System.out.println(RunResults.format(results));
		Map<URI, SuccessResult> groupByInput = RunResults.groupByInput(results);
		System.out.println(RunResults.format(groupByInput));
		System.out.println(RunResults.summary(groupByInput));
	}

}
