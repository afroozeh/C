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

public class CSharp {
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/CSharpPreprocessorCharLevel"));
	private static Grammar grammar = new LayoutWeaver().transform(new EBNFToBNF().transform(originalGrammar));
	private static Start start = grammar.getStartSymbol(Nonterminal.withName("CompilationUnit"));
	
	private static String roslyn = "/Users/aliafroozeh/corpus/CSharp/roslyn";
	private static String mvc = "/Users/aliafroozeh/corpus/CSharp/Mvc-6.0.0-beta5";
	private static String entityFramework = "/Users/aliafroozeh/corpus/CSharp/EntityFramework-7.0.0-beta5";
	
	public static void main(String[] args) {
		System.out.println(originalGrammar.getNonterminals().size());
		List<RunResult> results = IguanaRunner.builder(grammar, start)
                .setWarmupCount(2)
                .setRunCount(5)
//                .setRunGCInBetween(true)
//                .setLimit(10)
                .ignore("/Users/aliafroozeh/corpus/CSharp/roslyn/Src/Compilers/VisualBasic/Test/Semantic/Binding/T_1556342.cs")
                .addDirectory(roslyn, "cs", true)
                .addDirectory(entityFramework, "cs", true)
                .addDirectory(mvc, "cs", true)
//                .addFile("files/test.cs")
                .build()
                .run();
		
//		System.out.println(results);
		System.out.println(RunResults.format(results));
//		System.out.println(RunResults.format(RunResults.groupByInput(results)));
	}
	

}
