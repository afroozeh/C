package org.iguana;

import java.io.File;
import java.util.List;

import org.iguana.grammar.Grammar;
import org.iguana.grammar.symbol.Nonterminal;
import org.iguana.grammar.symbol.Start;
import org.iguana.grammar.transformation.DesugarAlignAndOffside;
import org.iguana.grammar.transformation.DesugarPrecedenceAndAssociativity;
import org.iguana.grammar.transformation.EBNFToBNF;
import org.iguana.grammar.transformation.LayoutWeaver;
import org.iguana.util.IguanaRunner;
import org.iguana.util.RunResult;
import org.iguana.util.RunResults;

public class Haskell {
	
	private static Grammar originalGrammar = Grammar.load(new File("grammar/HaskellDDCharLevel"));

	private static Grammar grammar;
	
	private static String ghc = "/Users/aliafroozeh/corpus/ghc-output/ghc";
	private static String cabal = "/Users/aliafroozeh/corpus/ghc-output/libs/cabal-Cabal-v1.22.4.0";
	private static String fay = "/Users/aliafroozeh/corpus/ghc-output/libs/fay-0.23.1.6";
	private static String git_annex = "/Users/aliafroozeh/corpus/ghc-output/libs/git-annex-5.20150710";
	
	static {
        DesugarAlignAndOffside desugarAlignAndOffside = new DesugarAlignAndOffside();
        desugarAlignAndOffside.doAlign();

        grammar = desugarAlignAndOffside.transform(originalGrammar);

        grammar = new EBNFToBNF().transform(grammar);

        desugarAlignAndOffside.doOffside();
        grammar = desugarAlignAndOffside.transform(grammar);

        grammar = new DesugarPrecedenceAndAssociativity().transform(grammar);

        grammar = new LayoutWeaver().transform(grammar);
	}

	
	private static Start start = grammar.getStartSymbol(Nonterminal.withName("Module"));
	
	public static void main(String[] args) {
		List<RunResult> results = IguanaRunner.builder(grammar, start)
                .setWarmupCount(0)
                .setRunCount(1)
                .setRunGCInBetween(false)
//                .setLimit(20)
                .addDirectory(ghc, "hs", true)
                .addDirectory(cabal, "hs", true)
                .addDirectory(fay, "hs", true)
                .addDirectory(git_annex, "hs", true)
                .build()
                .run();		
		
		System.out.println(RunResults.format(results));
	}

}
