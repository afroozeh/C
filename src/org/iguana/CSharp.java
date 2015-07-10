package org.iguana;

import java.io.File;
import java.util.List;

import org.iguana.grammar.Grammar;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Start;
import org.iguana.grammar.transformation.EBNFToBNF;
import org.iguana.grammar.transformation.LayoutWeaver;
import org.iguana.parser.ParseResult;
import org.iguana.util.IguanaRunner;
import org.iguana.util.RunResult;
import org.iguana.util.RunResults;

public class CSharp {
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/CSharpCharLevel"));
	private static Grammar grammar = new LayoutWeaver().transform(new EBNFToBNF().transform(originalGrammar));
	private static Start start = grammar.getStartSymbol(Nonterminal.withName("CompilationUnit"));
	
	private static String sourceDir = "/Users/aliafroozeh/corpus/CSharp/roslyn";
	
	public static void main(String[] args) {
		System.out.println(originalGrammar.getNonterminals().size());
		List<RunResult> results = IguanaRunner.builder(grammar, start)
                .setWarmupCount(0)
                .setRunCount(1)
                .setRunGCInBetween(true)
                .addFile("/Users/aliafroozeh/corpus/CSharp/roslyn/Src/Compilers/VisualBasic/Test/Semantic/Binding/T_1556342.cs")
//                .addDirectory(sourceDir, "cs", true)
                .build()
                .run();
		
		System.out.println(RunResults.format(RunResults.groupByInput(results)));
	}
	

}
