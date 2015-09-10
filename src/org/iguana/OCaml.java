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
import org.iguana.util.IguanaRunner;
import org.iguana.util.RunResult;
import org.iguana.util.RunResultUtil;
import org.iguana.util.SuccessResult;

public class OCaml {
	
	private static final DesugarPrecedenceAndAssociativity DESUGAR_PRECEDENCE_AND_ASSOCIATIVITY = new DesugarPrecedenceAndAssociativity();
	static { DESUGAR_PRECEDENCE_AND_ASSOCIATIVITY.setOP2(); }
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/OCamlCharLevelDD"));
	private static Grammar originalDDGrammar = Grammar.load(new File("grammar/OCamlCharLevelDD"));
	
	private static Grammar grammar = new LayoutWeaver().transform(DESUGAR_PRECEDENCE_AND_ASSOCIATIVITY.transform(new EBNFToBNF().transform(originalDDGrammar)));
//	private static Grammar grammar = new LayoutWeaver().transform(new EBNFToBNF().transform(originalGrammar));
//	private static Grammar grammar = new LayoutWeaver().transform(new OperatorPrecedence(originalGrammar.getPrecedencePatterns(), originalGrammar.getExceptPatterns()).transform(new EBNFToBNF().transform(originalGrammar)));
	
	private static Start start = grammar.getStartSymbol(Nonterminal.withName("CompilationUnit"));
	
	private static String ocaml = "/Users/aliafroozeh/corpus/ocaml";
	
	public static void main(String[] args) {
				
//		List<ParseResult> results = IguanaRunner.builder(grammar, Nonterminal.withName("StatementExpression")).addString("logger.info(\"Activator.start()\")").build().run();
		
//		System.out.println(originalGrammar.getNonterminals().size());
//		List<RunResult> results = IguanaRunner.builder(grammar, start)
//				                              .addFile("files/Test.ml")
//				                              .setWarmupCount(0)
//				                              .setRunCount(1)
////				                              .setRunGCInBetween(true)
//				                              .build()
//				                              .run();

//		System.out.println(results);
		
//		System.out.println(grammar);
		
		List<RunResult> results = IguanaRunner.builder(grammar, start)
				                              .setWarmupCount(5)
				                              .setRunCount(10)
//				                              .setRunGCInBetween(false)
				                              .setLimit(50)
				                              .addDirectory(ocaml, "ml", true)
//				                              .addFile("files/Test.ml")
				                              .build()
				                              .run();
		
//		System.out.println(results);
//		
		System.out.println(RunResultUtil.format(results));
		Map<URI, SuccessResult> groupByInput = RunResultUtil.groupByInput(results);
		System.out.println(RunResultUtil.format(groupByInput));
		System.out.println(RunResultUtil.summary(groupByInput));
	}

}
