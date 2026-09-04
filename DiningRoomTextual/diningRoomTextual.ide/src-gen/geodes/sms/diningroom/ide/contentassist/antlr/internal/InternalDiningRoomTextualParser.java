package geodes.sms.diningroom.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import geodes.sms.diningroom.services.DiningRoomTextualGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalDiningRoomTextualParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'Room'", "'{'", "'}'", "'furniture'", "','", "'Table'", "'x'", "'y'", "'around'", "'('", "')'", "'Chair'", "'order'", "'-'"
    };
    public static final int RULE_STRING=4;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int RULE_ID=5;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int RULE_INT=6;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalDiningRoomTextualParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalDiningRoomTextualParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalDiningRoomTextualParser.tokenNames; }
    public String getGrammarFileName() { return "InternalDiningRoomTextual.g"; }


    	private DiningRoomTextualGrammarAccess grammarAccess;

    	public void setGrammarAccess(DiningRoomTextualGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleRoom"
    // InternalDiningRoomTextual.g:53:1: entryRuleRoom : ruleRoom EOF ;
    public final void entryRuleRoom() throws RecognitionException {
        try {
            // InternalDiningRoomTextual.g:54:1: ( ruleRoom EOF )
            // InternalDiningRoomTextual.g:55:1: ruleRoom EOF
            {
             before(grammarAccess.getRoomRule()); 
            pushFollow(FOLLOW_1);
            ruleRoom();

            state._fsp--;

             after(grammarAccess.getRoomRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRoom"


    // $ANTLR start "ruleRoom"
    // InternalDiningRoomTextual.g:62:1: ruleRoom : ( ( rule__Room__Group__0 ) ) ;
    public final void ruleRoom() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:66:2: ( ( ( rule__Room__Group__0 ) ) )
            // InternalDiningRoomTextual.g:67:2: ( ( rule__Room__Group__0 ) )
            {
            // InternalDiningRoomTextual.g:67:2: ( ( rule__Room__Group__0 ) )
            // InternalDiningRoomTextual.g:68:3: ( rule__Room__Group__0 )
            {
             before(grammarAccess.getRoomAccess().getGroup()); 
            // InternalDiningRoomTextual.g:69:3: ( rule__Room__Group__0 )
            // InternalDiningRoomTextual.g:69:4: rule__Room__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Room__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRoomAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRoom"


    // $ANTLR start "entryRuleFurniture"
    // InternalDiningRoomTextual.g:78:1: entryRuleFurniture : ruleFurniture EOF ;
    public final void entryRuleFurniture() throws RecognitionException {
        try {
            // InternalDiningRoomTextual.g:79:1: ( ruleFurniture EOF )
            // InternalDiningRoomTextual.g:80:1: ruleFurniture EOF
            {
             before(grammarAccess.getFurnitureRule()); 
            pushFollow(FOLLOW_1);
            ruleFurniture();

            state._fsp--;

             after(grammarAccess.getFurnitureRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleFurniture"


    // $ANTLR start "ruleFurniture"
    // InternalDiningRoomTextual.g:87:1: ruleFurniture : ( ( rule__Furniture__Alternatives ) ) ;
    public final void ruleFurniture() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:91:2: ( ( ( rule__Furniture__Alternatives ) ) )
            // InternalDiningRoomTextual.g:92:2: ( ( rule__Furniture__Alternatives ) )
            {
            // InternalDiningRoomTextual.g:92:2: ( ( rule__Furniture__Alternatives ) )
            // InternalDiningRoomTextual.g:93:3: ( rule__Furniture__Alternatives )
            {
             before(grammarAccess.getFurnitureAccess().getAlternatives()); 
            // InternalDiningRoomTextual.g:94:3: ( rule__Furniture__Alternatives )
            // InternalDiningRoomTextual.g:94:4: rule__Furniture__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Furniture__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getFurnitureAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFurniture"


    // $ANTLR start "entryRuleEString"
    // InternalDiningRoomTextual.g:103:1: entryRuleEString : ruleEString EOF ;
    public final void entryRuleEString() throws RecognitionException {
        try {
            // InternalDiningRoomTextual.g:104:1: ( ruleEString EOF )
            // InternalDiningRoomTextual.g:105:1: ruleEString EOF
            {
             before(grammarAccess.getEStringRule()); 
            pushFollow(FOLLOW_1);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getEStringRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleEString"


    // $ANTLR start "ruleEString"
    // InternalDiningRoomTextual.g:112:1: ruleEString : ( ( rule__EString__Alternatives ) ) ;
    public final void ruleEString() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:116:2: ( ( ( rule__EString__Alternatives ) ) )
            // InternalDiningRoomTextual.g:117:2: ( ( rule__EString__Alternatives ) )
            {
            // InternalDiningRoomTextual.g:117:2: ( ( rule__EString__Alternatives ) )
            // InternalDiningRoomTextual.g:118:3: ( rule__EString__Alternatives )
            {
             before(grammarAccess.getEStringAccess().getAlternatives()); 
            // InternalDiningRoomTextual.g:119:3: ( rule__EString__Alternatives )
            // InternalDiningRoomTextual.g:119:4: rule__EString__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__EString__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getEStringAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEString"


    // $ANTLR start "entryRuleTable"
    // InternalDiningRoomTextual.g:128:1: entryRuleTable : ruleTable EOF ;
    public final void entryRuleTable() throws RecognitionException {
        try {
            // InternalDiningRoomTextual.g:129:1: ( ruleTable EOF )
            // InternalDiningRoomTextual.g:130:1: ruleTable EOF
            {
             before(grammarAccess.getTableRule()); 
            pushFollow(FOLLOW_1);
            ruleTable();

            state._fsp--;

             after(grammarAccess.getTableRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTable"


    // $ANTLR start "ruleTable"
    // InternalDiningRoomTextual.g:137:1: ruleTable : ( ( rule__Table__Group__0 ) ) ;
    public final void ruleTable() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:141:2: ( ( ( rule__Table__Group__0 ) ) )
            // InternalDiningRoomTextual.g:142:2: ( ( rule__Table__Group__0 ) )
            {
            // InternalDiningRoomTextual.g:142:2: ( ( rule__Table__Group__0 ) )
            // InternalDiningRoomTextual.g:143:3: ( rule__Table__Group__0 )
            {
             before(grammarAccess.getTableAccess().getGroup()); 
            // InternalDiningRoomTextual.g:144:3: ( rule__Table__Group__0 )
            // InternalDiningRoomTextual.g:144:4: rule__Table__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Table__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTableAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTable"


    // $ANTLR start "entryRuleChair"
    // InternalDiningRoomTextual.g:153:1: entryRuleChair : ruleChair EOF ;
    public final void entryRuleChair() throws RecognitionException {
        try {
            // InternalDiningRoomTextual.g:154:1: ( ruleChair EOF )
            // InternalDiningRoomTextual.g:155:1: ruleChair EOF
            {
             before(grammarAccess.getChairRule()); 
            pushFollow(FOLLOW_1);
            ruleChair();

            state._fsp--;

             after(grammarAccess.getChairRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleChair"


    // $ANTLR start "ruleChair"
    // InternalDiningRoomTextual.g:162:1: ruleChair : ( ( rule__Chair__Group__0 ) ) ;
    public final void ruleChair() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:166:2: ( ( ( rule__Chair__Group__0 ) ) )
            // InternalDiningRoomTextual.g:167:2: ( ( rule__Chair__Group__0 ) )
            {
            // InternalDiningRoomTextual.g:167:2: ( ( rule__Chair__Group__0 ) )
            // InternalDiningRoomTextual.g:168:3: ( rule__Chair__Group__0 )
            {
             before(grammarAccess.getChairAccess().getGroup()); 
            // InternalDiningRoomTextual.g:169:3: ( rule__Chair__Group__0 )
            // InternalDiningRoomTextual.g:169:4: rule__Chair__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Chair__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getChairAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleChair"


    // $ANTLR start "entryRuleEInt"
    // InternalDiningRoomTextual.g:178:1: entryRuleEInt : ruleEInt EOF ;
    public final void entryRuleEInt() throws RecognitionException {
        try {
            // InternalDiningRoomTextual.g:179:1: ( ruleEInt EOF )
            // InternalDiningRoomTextual.g:180:1: ruleEInt EOF
            {
             before(grammarAccess.getEIntRule()); 
            pushFollow(FOLLOW_1);
            ruleEInt();

            state._fsp--;

             after(grammarAccess.getEIntRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleEInt"


    // $ANTLR start "ruleEInt"
    // InternalDiningRoomTextual.g:187:1: ruleEInt : ( ( rule__EInt__Group__0 ) ) ;
    public final void ruleEInt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:191:2: ( ( ( rule__EInt__Group__0 ) ) )
            // InternalDiningRoomTextual.g:192:2: ( ( rule__EInt__Group__0 ) )
            {
            // InternalDiningRoomTextual.g:192:2: ( ( rule__EInt__Group__0 ) )
            // InternalDiningRoomTextual.g:193:3: ( rule__EInt__Group__0 )
            {
             before(grammarAccess.getEIntAccess().getGroup()); 
            // InternalDiningRoomTextual.g:194:3: ( rule__EInt__Group__0 )
            // InternalDiningRoomTextual.g:194:4: rule__EInt__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__EInt__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getEIntAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEInt"


    // $ANTLR start "rule__Furniture__Alternatives"
    // InternalDiningRoomTextual.g:202:1: rule__Furniture__Alternatives : ( ( ruleTable ) | ( ruleChair ) );
    public final void rule__Furniture__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:206:1: ( ( ruleTable ) | ( ruleChair ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==16) ) {
                alt1=1;
            }
            else if ( (LA1_0==22) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalDiningRoomTextual.g:207:2: ( ruleTable )
                    {
                    // InternalDiningRoomTextual.g:207:2: ( ruleTable )
                    // InternalDiningRoomTextual.g:208:3: ruleTable
                    {
                     before(grammarAccess.getFurnitureAccess().getTableParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleTable();

                    state._fsp--;

                     after(grammarAccess.getFurnitureAccess().getTableParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalDiningRoomTextual.g:213:2: ( ruleChair )
                    {
                    // InternalDiningRoomTextual.g:213:2: ( ruleChair )
                    // InternalDiningRoomTextual.g:214:3: ruleChair
                    {
                     before(grammarAccess.getFurnitureAccess().getChairParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleChair();

                    state._fsp--;

                     after(grammarAccess.getFurnitureAccess().getChairParserRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Furniture__Alternatives"


    // $ANTLR start "rule__EString__Alternatives"
    // InternalDiningRoomTextual.g:223:1: rule__EString__Alternatives : ( ( RULE_STRING ) | ( RULE_ID ) );
    public final void rule__EString__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:227:1: ( ( RULE_STRING ) | ( RULE_ID ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==RULE_STRING) ) {
                alt2=1;
            }
            else if ( (LA2_0==RULE_ID) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalDiningRoomTextual.g:228:2: ( RULE_STRING )
                    {
                    // InternalDiningRoomTextual.g:228:2: ( RULE_STRING )
                    // InternalDiningRoomTextual.g:229:3: RULE_STRING
                    {
                     before(grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0()); 
                    match(input,RULE_STRING,FOLLOW_2); 
                     after(grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalDiningRoomTextual.g:234:2: ( RULE_ID )
                    {
                    // InternalDiningRoomTextual.g:234:2: ( RULE_ID )
                    // InternalDiningRoomTextual.g:235:3: RULE_ID
                    {
                     before(grammarAccess.getEStringAccess().getIDTerminalRuleCall_1()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getEStringAccess().getIDTerminalRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EString__Alternatives"


    // $ANTLR start "rule__Room__Group__0"
    // InternalDiningRoomTextual.g:244:1: rule__Room__Group__0 : rule__Room__Group__0__Impl rule__Room__Group__1 ;
    public final void rule__Room__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:248:1: ( rule__Room__Group__0__Impl rule__Room__Group__1 )
            // InternalDiningRoomTextual.g:249:2: rule__Room__Group__0__Impl rule__Room__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Room__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Room__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__0"


    // $ANTLR start "rule__Room__Group__0__Impl"
    // InternalDiningRoomTextual.g:256:1: rule__Room__Group__0__Impl : ( () ) ;
    public final void rule__Room__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:260:1: ( ( () ) )
            // InternalDiningRoomTextual.g:261:1: ( () )
            {
            // InternalDiningRoomTextual.g:261:1: ( () )
            // InternalDiningRoomTextual.g:262:2: ()
            {
             before(grammarAccess.getRoomAccess().getRoomAction_0()); 
            // InternalDiningRoomTextual.g:263:2: ()
            // InternalDiningRoomTextual.g:263:3: 
            {
            }

             after(grammarAccess.getRoomAccess().getRoomAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__0__Impl"


    // $ANTLR start "rule__Room__Group__1"
    // InternalDiningRoomTextual.g:271:1: rule__Room__Group__1 : rule__Room__Group__1__Impl rule__Room__Group__2 ;
    public final void rule__Room__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:275:1: ( rule__Room__Group__1__Impl rule__Room__Group__2 )
            // InternalDiningRoomTextual.g:276:2: rule__Room__Group__1__Impl rule__Room__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__Room__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Room__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__1"


    // $ANTLR start "rule__Room__Group__1__Impl"
    // InternalDiningRoomTextual.g:283:1: rule__Room__Group__1__Impl : ( 'Room' ) ;
    public final void rule__Room__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:287:1: ( ( 'Room' ) )
            // InternalDiningRoomTextual.g:288:1: ( 'Room' )
            {
            // InternalDiningRoomTextual.g:288:1: ( 'Room' )
            // InternalDiningRoomTextual.g:289:2: 'Room'
            {
             before(grammarAccess.getRoomAccess().getRoomKeyword_1()); 
            match(input,11,FOLLOW_2); 
             after(grammarAccess.getRoomAccess().getRoomKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__1__Impl"


    // $ANTLR start "rule__Room__Group__2"
    // InternalDiningRoomTextual.g:298:1: rule__Room__Group__2 : rule__Room__Group__2__Impl rule__Room__Group__3 ;
    public final void rule__Room__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:302:1: ( rule__Room__Group__2__Impl rule__Room__Group__3 )
            // InternalDiningRoomTextual.g:303:2: rule__Room__Group__2__Impl rule__Room__Group__3
            {
            pushFollow(FOLLOW_5);
            rule__Room__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Room__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__2"


    // $ANTLR start "rule__Room__Group__2__Impl"
    // InternalDiningRoomTextual.g:310:1: rule__Room__Group__2__Impl : ( ( rule__Room__NameAssignment_2 ) ) ;
    public final void rule__Room__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:314:1: ( ( ( rule__Room__NameAssignment_2 ) ) )
            // InternalDiningRoomTextual.g:315:1: ( ( rule__Room__NameAssignment_2 ) )
            {
            // InternalDiningRoomTextual.g:315:1: ( ( rule__Room__NameAssignment_2 ) )
            // InternalDiningRoomTextual.g:316:2: ( rule__Room__NameAssignment_2 )
            {
             before(grammarAccess.getRoomAccess().getNameAssignment_2()); 
            // InternalDiningRoomTextual.g:317:2: ( rule__Room__NameAssignment_2 )
            // InternalDiningRoomTextual.g:317:3: rule__Room__NameAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Room__NameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getRoomAccess().getNameAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__2__Impl"


    // $ANTLR start "rule__Room__Group__3"
    // InternalDiningRoomTextual.g:325:1: rule__Room__Group__3 : rule__Room__Group__3__Impl rule__Room__Group__4 ;
    public final void rule__Room__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:329:1: ( rule__Room__Group__3__Impl rule__Room__Group__4 )
            // InternalDiningRoomTextual.g:330:2: rule__Room__Group__3__Impl rule__Room__Group__4
            {
            pushFollow(FOLLOW_6);
            rule__Room__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Room__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__3"


    // $ANTLR start "rule__Room__Group__3__Impl"
    // InternalDiningRoomTextual.g:337:1: rule__Room__Group__3__Impl : ( '{' ) ;
    public final void rule__Room__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:341:1: ( ( '{' ) )
            // InternalDiningRoomTextual.g:342:1: ( '{' )
            {
            // InternalDiningRoomTextual.g:342:1: ( '{' )
            // InternalDiningRoomTextual.g:343:2: '{'
            {
             before(grammarAccess.getRoomAccess().getLeftCurlyBracketKeyword_3()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getRoomAccess().getLeftCurlyBracketKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__3__Impl"


    // $ANTLR start "rule__Room__Group__4"
    // InternalDiningRoomTextual.g:352:1: rule__Room__Group__4 : rule__Room__Group__4__Impl rule__Room__Group__5 ;
    public final void rule__Room__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:356:1: ( rule__Room__Group__4__Impl rule__Room__Group__5 )
            // InternalDiningRoomTextual.g:357:2: rule__Room__Group__4__Impl rule__Room__Group__5
            {
            pushFollow(FOLLOW_6);
            rule__Room__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Room__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__4"


    // $ANTLR start "rule__Room__Group__4__Impl"
    // InternalDiningRoomTextual.g:364:1: rule__Room__Group__4__Impl : ( ( rule__Room__Group_4__0 )? ) ;
    public final void rule__Room__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:368:1: ( ( ( rule__Room__Group_4__0 )? ) )
            // InternalDiningRoomTextual.g:369:1: ( ( rule__Room__Group_4__0 )? )
            {
            // InternalDiningRoomTextual.g:369:1: ( ( rule__Room__Group_4__0 )? )
            // InternalDiningRoomTextual.g:370:2: ( rule__Room__Group_4__0 )?
            {
             before(grammarAccess.getRoomAccess().getGroup_4()); 
            // InternalDiningRoomTextual.g:371:2: ( rule__Room__Group_4__0 )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==14) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalDiningRoomTextual.g:371:3: rule__Room__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Room__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRoomAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__4__Impl"


    // $ANTLR start "rule__Room__Group__5"
    // InternalDiningRoomTextual.g:379:1: rule__Room__Group__5 : rule__Room__Group__5__Impl ;
    public final void rule__Room__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:383:1: ( rule__Room__Group__5__Impl )
            // InternalDiningRoomTextual.g:384:2: rule__Room__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Room__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__5"


    // $ANTLR start "rule__Room__Group__5__Impl"
    // InternalDiningRoomTextual.g:390:1: rule__Room__Group__5__Impl : ( '}' ) ;
    public final void rule__Room__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:394:1: ( ( '}' ) )
            // InternalDiningRoomTextual.g:395:1: ( '}' )
            {
            // InternalDiningRoomTextual.g:395:1: ( '}' )
            // InternalDiningRoomTextual.g:396:2: '}'
            {
             before(grammarAccess.getRoomAccess().getRightCurlyBracketKeyword_5()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getRoomAccess().getRightCurlyBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group__5__Impl"


    // $ANTLR start "rule__Room__Group_4__0"
    // InternalDiningRoomTextual.g:406:1: rule__Room__Group_4__0 : rule__Room__Group_4__0__Impl rule__Room__Group_4__1 ;
    public final void rule__Room__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:410:1: ( rule__Room__Group_4__0__Impl rule__Room__Group_4__1 )
            // InternalDiningRoomTextual.g:411:2: rule__Room__Group_4__0__Impl rule__Room__Group_4__1
            {
            pushFollow(FOLLOW_5);
            rule__Room__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Room__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4__0"


    // $ANTLR start "rule__Room__Group_4__0__Impl"
    // InternalDiningRoomTextual.g:418:1: rule__Room__Group_4__0__Impl : ( 'furniture' ) ;
    public final void rule__Room__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:422:1: ( ( 'furniture' ) )
            // InternalDiningRoomTextual.g:423:1: ( 'furniture' )
            {
            // InternalDiningRoomTextual.g:423:1: ( 'furniture' )
            // InternalDiningRoomTextual.g:424:2: 'furniture'
            {
             before(grammarAccess.getRoomAccess().getFurnitureKeyword_4_0()); 
            match(input,14,FOLLOW_2); 
             after(grammarAccess.getRoomAccess().getFurnitureKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4__0__Impl"


    // $ANTLR start "rule__Room__Group_4__1"
    // InternalDiningRoomTextual.g:433:1: rule__Room__Group_4__1 : rule__Room__Group_4__1__Impl rule__Room__Group_4__2 ;
    public final void rule__Room__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:437:1: ( rule__Room__Group_4__1__Impl rule__Room__Group_4__2 )
            // InternalDiningRoomTextual.g:438:2: rule__Room__Group_4__1__Impl rule__Room__Group_4__2
            {
            pushFollow(FOLLOW_7);
            rule__Room__Group_4__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Room__Group_4__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4__1"


    // $ANTLR start "rule__Room__Group_4__1__Impl"
    // InternalDiningRoomTextual.g:445:1: rule__Room__Group_4__1__Impl : ( '{' ) ;
    public final void rule__Room__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:449:1: ( ( '{' ) )
            // InternalDiningRoomTextual.g:450:1: ( '{' )
            {
            // InternalDiningRoomTextual.g:450:1: ( '{' )
            // InternalDiningRoomTextual.g:451:2: '{'
            {
             before(grammarAccess.getRoomAccess().getLeftCurlyBracketKeyword_4_1()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getRoomAccess().getLeftCurlyBracketKeyword_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4__1__Impl"


    // $ANTLR start "rule__Room__Group_4__2"
    // InternalDiningRoomTextual.g:460:1: rule__Room__Group_4__2 : rule__Room__Group_4__2__Impl rule__Room__Group_4__3 ;
    public final void rule__Room__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:464:1: ( rule__Room__Group_4__2__Impl rule__Room__Group_4__3 )
            // InternalDiningRoomTextual.g:465:2: rule__Room__Group_4__2__Impl rule__Room__Group_4__3
            {
            pushFollow(FOLLOW_8);
            rule__Room__Group_4__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Room__Group_4__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4__2"


    // $ANTLR start "rule__Room__Group_4__2__Impl"
    // InternalDiningRoomTextual.g:472:1: rule__Room__Group_4__2__Impl : ( ( rule__Room__FurnitureAssignment_4_2 ) ) ;
    public final void rule__Room__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:476:1: ( ( ( rule__Room__FurnitureAssignment_4_2 ) ) )
            // InternalDiningRoomTextual.g:477:1: ( ( rule__Room__FurnitureAssignment_4_2 ) )
            {
            // InternalDiningRoomTextual.g:477:1: ( ( rule__Room__FurnitureAssignment_4_2 ) )
            // InternalDiningRoomTextual.g:478:2: ( rule__Room__FurnitureAssignment_4_2 )
            {
             before(grammarAccess.getRoomAccess().getFurnitureAssignment_4_2()); 
            // InternalDiningRoomTextual.g:479:2: ( rule__Room__FurnitureAssignment_4_2 )
            // InternalDiningRoomTextual.g:479:3: rule__Room__FurnitureAssignment_4_2
            {
            pushFollow(FOLLOW_2);
            rule__Room__FurnitureAssignment_4_2();

            state._fsp--;


            }

             after(grammarAccess.getRoomAccess().getFurnitureAssignment_4_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4__2__Impl"


    // $ANTLR start "rule__Room__Group_4__3"
    // InternalDiningRoomTextual.g:487:1: rule__Room__Group_4__3 : rule__Room__Group_4__3__Impl rule__Room__Group_4__4 ;
    public final void rule__Room__Group_4__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:491:1: ( rule__Room__Group_4__3__Impl rule__Room__Group_4__4 )
            // InternalDiningRoomTextual.g:492:2: rule__Room__Group_4__3__Impl rule__Room__Group_4__4
            {
            pushFollow(FOLLOW_8);
            rule__Room__Group_4__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Room__Group_4__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4__3"


    // $ANTLR start "rule__Room__Group_4__3__Impl"
    // InternalDiningRoomTextual.g:499:1: rule__Room__Group_4__3__Impl : ( ( rule__Room__Group_4_3__0 )* ) ;
    public final void rule__Room__Group_4__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:503:1: ( ( ( rule__Room__Group_4_3__0 )* ) )
            // InternalDiningRoomTextual.g:504:1: ( ( rule__Room__Group_4_3__0 )* )
            {
            // InternalDiningRoomTextual.g:504:1: ( ( rule__Room__Group_4_3__0 )* )
            // InternalDiningRoomTextual.g:505:2: ( rule__Room__Group_4_3__0 )*
            {
             before(grammarAccess.getRoomAccess().getGroup_4_3()); 
            // InternalDiningRoomTextual.g:506:2: ( rule__Room__Group_4_3__0 )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==15) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalDiningRoomTextual.g:506:3: rule__Room__Group_4_3__0
            	    {
            	    pushFollow(FOLLOW_9);
            	    rule__Room__Group_4_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

             after(grammarAccess.getRoomAccess().getGroup_4_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4__3__Impl"


    // $ANTLR start "rule__Room__Group_4__4"
    // InternalDiningRoomTextual.g:514:1: rule__Room__Group_4__4 : rule__Room__Group_4__4__Impl ;
    public final void rule__Room__Group_4__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:518:1: ( rule__Room__Group_4__4__Impl )
            // InternalDiningRoomTextual.g:519:2: rule__Room__Group_4__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Room__Group_4__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4__4"


    // $ANTLR start "rule__Room__Group_4__4__Impl"
    // InternalDiningRoomTextual.g:525:1: rule__Room__Group_4__4__Impl : ( '}' ) ;
    public final void rule__Room__Group_4__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:529:1: ( ( '}' ) )
            // InternalDiningRoomTextual.g:530:1: ( '}' )
            {
            // InternalDiningRoomTextual.g:530:1: ( '}' )
            // InternalDiningRoomTextual.g:531:2: '}'
            {
             before(grammarAccess.getRoomAccess().getRightCurlyBracketKeyword_4_4()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getRoomAccess().getRightCurlyBracketKeyword_4_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4__4__Impl"


    // $ANTLR start "rule__Room__Group_4_3__0"
    // InternalDiningRoomTextual.g:541:1: rule__Room__Group_4_3__0 : rule__Room__Group_4_3__0__Impl rule__Room__Group_4_3__1 ;
    public final void rule__Room__Group_4_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:545:1: ( rule__Room__Group_4_3__0__Impl rule__Room__Group_4_3__1 )
            // InternalDiningRoomTextual.g:546:2: rule__Room__Group_4_3__0__Impl rule__Room__Group_4_3__1
            {
            pushFollow(FOLLOW_7);
            rule__Room__Group_4_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Room__Group_4_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4_3__0"


    // $ANTLR start "rule__Room__Group_4_3__0__Impl"
    // InternalDiningRoomTextual.g:553:1: rule__Room__Group_4_3__0__Impl : ( ',' ) ;
    public final void rule__Room__Group_4_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:557:1: ( ( ',' ) )
            // InternalDiningRoomTextual.g:558:1: ( ',' )
            {
            // InternalDiningRoomTextual.g:558:1: ( ',' )
            // InternalDiningRoomTextual.g:559:2: ','
            {
             before(grammarAccess.getRoomAccess().getCommaKeyword_4_3_0()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getRoomAccess().getCommaKeyword_4_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4_3__0__Impl"


    // $ANTLR start "rule__Room__Group_4_3__1"
    // InternalDiningRoomTextual.g:568:1: rule__Room__Group_4_3__1 : rule__Room__Group_4_3__1__Impl ;
    public final void rule__Room__Group_4_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:572:1: ( rule__Room__Group_4_3__1__Impl )
            // InternalDiningRoomTextual.g:573:2: rule__Room__Group_4_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Room__Group_4_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4_3__1"


    // $ANTLR start "rule__Room__Group_4_3__1__Impl"
    // InternalDiningRoomTextual.g:579:1: rule__Room__Group_4_3__1__Impl : ( ( rule__Room__FurnitureAssignment_4_3_1 ) ) ;
    public final void rule__Room__Group_4_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:583:1: ( ( ( rule__Room__FurnitureAssignment_4_3_1 ) ) )
            // InternalDiningRoomTextual.g:584:1: ( ( rule__Room__FurnitureAssignment_4_3_1 ) )
            {
            // InternalDiningRoomTextual.g:584:1: ( ( rule__Room__FurnitureAssignment_4_3_1 ) )
            // InternalDiningRoomTextual.g:585:2: ( rule__Room__FurnitureAssignment_4_3_1 )
            {
             before(grammarAccess.getRoomAccess().getFurnitureAssignment_4_3_1()); 
            // InternalDiningRoomTextual.g:586:2: ( rule__Room__FurnitureAssignment_4_3_1 )
            // InternalDiningRoomTextual.g:586:3: rule__Room__FurnitureAssignment_4_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Room__FurnitureAssignment_4_3_1();

            state._fsp--;


            }

             after(grammarAccess.getRoomAccess().getFurnitureAssignment_4_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__Group_4_3__1__Impl"


    // $ANTLR start "rule__Table__Group__0"
    // InternalDiningRoomTextual.g:595:1: rule__Table__Group__0 : rule__Table__Group__0__Impl rule__Table__Group__1 ;
    public final void rule__Table__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:599:1: ( rule__Table__Group__0__Impl rule__Table__Group__1 )
            // InternalDiningRoomTextual.g:600:2: rule__Table__Group__0__Impl rule__Table__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__Table__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__0"


    // $ANTLR start "rule__Table__Group__0__Impl"
    // InternalDiningRoomTextual.g:607:1: rule__Table__Group__0__Impl : ( () ) ;
    public final void rule__Table__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:611:1: ( ( () ) )
            // InternalDiningRoomTextual.g:612:1: ( () )
            {
            // InternalDiningRoomTextual.g:612:1: ( () )
            // InternalDiningRoomTextual.g:613:2: ()
            {
             before(grammarAccess.getTableAccess().getTableAction_0()); 
            // InternalDiningRoomTextual.g:614:2: ()
            // InternalDiningRoomTextual.g:614:3: 
            {
            }

             after(grammarAccess.getTableAccess().getTableAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__0__Impl"


    // $ANTLR start "rule__Table__Group__1"
    // InternalDiningRoomTextual.g:622:1: rule__Table__Group__1 : rule__Table__Group__1__Impl rule__Table__Group__2 ;
    public final void rule__Table__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:626:1: ( rule__Table__Group__1__Impl rule__Table__Group__2 )
            // InternalDiningRoomTextual.g:627:2: rule__Table__Group__1__Impl rule__Table__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__Table__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__1"


    // $ANTLR start "rule__Table__Group__1__Impl"
    // InternalDiningRoomTextual.g:634:1: rule__Table__Group__1__Impl : ( 'Table' ) ;
    public final void rule__Table__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:638:1: ( ( 'Table' ) )
            // InternalDiningRoomTextual.g:639:1: ( 'Table' )
            {
            // InternalDiningRoomTextual.g:639:1: ( 'Table' )
            // InternalDiningRoomTextual.g:640:2: 'Table'
            {
             before(grammarAccess.getTableAccess().getTableKeyword_1()); 
            match(input,16,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getTableKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__1__Impl"


    // $ANTLR start "rule__Table__Group__2"
    // InternalDiningRoomTextual.g:649:1: rule__Table__Group__2 : rule__Table__Group__2__Impl rule__Table__Group__3 ;
    public final void rule__Table__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:653:1: ( rule__Table__Group__2__Impl rule__Table__Group__3 )
            // InternalDiningRoomTextual.g:654:2: rule__Table__Group__2__Impl rule__Table__Group__3
            {
            pushFollow(FOLLOW_5);
            rule__Table__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__2"


    // $ANTLR start "rule__Table__Group__2__Impl"
    // InternalDiningRoomTextual.g:661:1: rule__Table__Group__2__Impl : ( ( rule__Table__NameAssignment_2 ) ) ;
    public final void rule__Table__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:665:1: ( ( ( rule__Table__NameAssignment_2 ) ) )
            // InternalDiningRoomTextual.g:666:1: ( ( rule__Table__NameAssignment_2 ) )
            {
            // InternalDiningRoomTextual.g:666:1: ( ( rule__Table__NameAssignment_2 ) )
            // InternalDiningRoomTextual.g:667:2: ( rule__Table__NameAssignment_2 )
            {
             before(grammarAccess.getTableAccess().getNameAssignment_2()); 
            // InternalDiningRoomTextual.g:668:2: ( rule__Table__NameAssignment_2 )
            // InternalDiningRoomTextual.g:668:3: rule__Table__NameAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Table__NameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getTableAccess().getNameAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__2__Impl"


    // $ANTLR start "rule__Table__Group__3"
    // InternalDiningRoomTextual.g:676:1: rule__Table__Group__3 : rule__Table__Group__3__Impl rule__Table__Group__4 ;
    public final void rule__Table__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:680:1: ( rule__Table__Group__3__Impl rule__Table__Group__4 )
            // InternalDiningRoomTextual.g:681:2: rule__Table__Group__3__Impl rule__Table__Group__4
            {
            pushFollow(FOLLOW_11);
            rule__Table__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__3"


    // $ANTLR start "rule__Table__Group__3__Impl"
    // InternalDiningRoomTextual.g:688:1: rule__Table__Group__3__Impl : ( '{' ) ;
    public final void rule__Table__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:692:1: ( ( '{' ) )
            // InternalDiningRoomTextual.g:693:1: ( '{' )
            {
            // InternalDiningRoomTextual.g:693:1: ( '{' )
            // InternalDiningRoomTextual.g:694:2: '{'
            {
             before(grammarAccess.getTableAccess().getLeftCurlyBracketKeyword_3()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getLeftCurlyBracketKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__3__Impl"


    // $ANTLR start "rule__Table__Group__4"
    // InternalDiningRoomTextual.g:703:1: rule__Table__Group__4 : rule__Table__Group__4__Impl rule__Table__Group__5 ;
    public final void rule__Table__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:707:1: ( rule__Table__Group__4__Impl rule__Table__Group__5 )
            // InternalDiningRoomTextual.g:708:2: rule__Table__Group__4__Impl rule__Table__Group__5
            {
            pushFollow(FOLLOW_11);
            rule__Table__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__4"


    // $ANTLR start "rule__Table__Group__4__Impl"
    // InternalDiningRoomTextual.g:715:1: rule__Table__Group__4__Impl : ( ( rule__Table__Group_4__0 )? ) ;
    public final void rule__Table__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:719:1: ( ( ( rule__Table__Group_4__0 )? ) )
            // InternalDiningRoomTextual.g:720:1: ( ( rule__Table__Group_4__0 )? )
            {
            // InternalDiningRoomTextual.g:720:1: ( ( rule__Table__Group_4__0 )? )
            // InternalDiningRoomTextual.g:721:2: ( rule__Table__Group_4__0 )?
            {
             before(grammarAccess.getTableAccess().getGroup_4()); 
            // InternalDiningRoomTextual.g:722:2: ( rule__Table__Group_4__0 )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==17) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalDiningRoomTextual.g:722:3: rule__Table__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Table__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTableAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__4__Impl"


    // $ANTLR start "rule__Table__Group__5"
    // InternalDiningRoomTextual.g:730:1: rule__Table__Group__5 : rule__Table__Group__5__Impl rule__Table__Group__6 ;
    public final void rule__Table__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:734:1: ( rule__Table__Group__5__Impl rule__Table__Group__6 )
            // InternalDiningRoomTextual.g:735:2: rule__Table__Group__5__Impl rule__Table__Group__6
            {
            pushFollow(FOLLOW_11);
            rule__Table__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__5"


    // $ANTLR start "rule__Table__Group__5__Impl"
    // InternalDiningRoomTextual.g:742:1: rule__Table__Group__5__Impl : ( ( rule__Table__Group_5__0 )? ) ;
    public final void rule__Table__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:746:1: ( ( ( rule__Table__Group_5__0 )? ) )
            // InternalDiningRoomTextual.g:747:1: ( ( rule__Table__Group_5__0 )? )
            {
            // InternalDiningRoomTextual.g:747:1: ( ( rule__Table__Group_5__0 )? )
            // InternalDiningRoomTextual.g:748:2: ( rule__Table__Group_5__0 )?
            {
             before(grammarAccess.getTableAccess().getGroup_5()); 
            // InternalDiningRoomTextual.g:749:2: ( rule__Table__Group_5__0 )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==18) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalDiningRoomTextual.g:749:3: rule__Table__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Table__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTableAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__5__Impl"


    // $ANTLR start "rule__Table__Group__6"
    // InternalDiningRoomTextual.g:757:1: rule__Table__Group__6 : rule__Table__Group__6__Impl rule__Table__Group__7 ;
    public final void rule__Table__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:761:1: ( rule__Table__Group__6__Impl rule__Table__Group__7 )
            // InternalDiningRoomTextual.g:762:2: rule__Table__Group__6__Impl rule__Table__Group__7
            {
            pushFollow(FOLLOW_11);
            rule__Table__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__6"


    // $ANTLR start "rule__Table__Group__6__Impl"
    // InternalDiningRoomTextual.g:769:1: rule__Table__Group__6__Impl : ( ( rule__Table__Group_6__0 )? ) ;
    public final void rule__Table__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:773:1: ( ( ( rule__Table__Group_6__0 )? ) )
            // InternalDiningRoomTextual.g:774:1: ( ( rule__Table__Group_6__0 )? )
            {
            // InternalDiningRoomTextual.g:774:1: ( ( rule__Table__Group_6__0 )? )
            // InternalDiningRoomTextual.g:775:2: ( rule__Table__Group_6__0 )?
            {
             before(grammarAccess.getTableAccess().getGroup_6()); 
            // InternalDiningRoomTextual.g:776:2: ( rule__Table__Group_6__0 )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==19) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalDiningRoomTextual.g:776:3: rule__Table__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Table__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTableAccess().getGroup_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__6__Impl"


    // $ANTLR start "rule__Table__Group__7"
    // InternalDiningRoomTextual.g:784:1: rule__Table__Group__7 : rule__Table__Group__7__Impl ;
    public final void rule__Table__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:788:1: ( rule__Table__Group__7__Impl )
            // InternalDiningRoomTextual.g:789:2: rule__Table__Group__7__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Table__Group__7__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__7"


    // $ANTLR start "rule__Table__Group__7__Impl"
    // InternalDiningRoomTextual.g:795:1: rule__Table__Group__7__Impl : ( '}' ) ;
    public final void rule__Table__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:799:1: ( ( '}' ) )
            // InternalDiningRoomTextual.g:800:1: ( '}' )
            {
            // InternalDiningRoomTextual.g:800:1: ( '}' )
            // InternalDiningRoomTextual.g:801:2: '}'
            {
             before(grammarAccess.getTableAccess().getRightCurlyBracketKeyword_7()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getRightCurlyBracketKeyword_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__7__Impl"


    // $ANTLR start "rule__Table__Group_4__0"
    // InternalDiningRoomTextual.g:811:1: rule__Table__Group_4__0 : rule__Table__Group_4__0__Impl rule__Table__Group_4__1 ;
    public final void rule__Table__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:815:1: ( rule__Table__Group_4__0__Impl rule__Table__Group_4__1 )
            // InternalDiningRoomTextual.g:816:2: rule__Table__Group_4__0__Impl rule__Table__Group_4__1
            {
            pushFollow(FOLLOW_12);
            rule__Table__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_4__0"


    // $ANTLR start "rule__Table__Group_4__0__Impl"
    // InternalDiningRoomTextual.g:823:1: rule__Table__Group_4__0__Impl : ( 'x' ) ;
    public final void rule__Table__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:827:1: ( ( 'x' ) )
            // InternalDiningRoomTextual.g:828:1: ( 'x' )
            {
            // InternalDiningRoomTextual.g:828:1: ( 'x' )
            // InternalDiningRoomTextual.g:829:2: 'x'
            {
             before(grammarAccess.getTableAccess().getXKeyword_4_0()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getXKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_4__0__Impl"


    // $ANTLR start "rule__Table__Group_4__1"
    // InternalDiningRoomTextual.g:838:1: rule__Table__Group_4__1 : rule__Table__Group_4__1__Impl ;
    public final void rule__Table__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:842:1: ( rule__Table__Group_4__1__Impl )
            // InternalDiningRoomTextual.g:843:2: rule__Table__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Table__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_4__1"


    // $ANTLR start "rule__Table__Group_4__1__Impl"
    // InternalDiningRoomTextual.g:849:1: rule__Table__Group_4__1__Impl : ( ( rule__Table__XAssignment_4_1 ) ) ;
    public final void rule__Table__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:853:1: ( ( ( rule__Table__XAssignment_4_1 ) ) )
            // InternalDiningRoomTextual.g:854:1: ( ( rule__Table__XAssignment_4_1 ) )
            {
            // InternalDiningRoomTextual.g:854:1: ( ( rule__Table__XAssignment_4_1 ) )
            // InternalDiningRoomTextual.g:855:2: ( rule__Table__XAssignment_4_1 )
            {
             before(grammarAccess.getTableAccess().getXAssignment_4_1()); 
            // InternalDiningRoomTextual.g:856:2: ( rule__Table__XAssignment_4_1 )
            // InternalDiningRoomTextual.g:856:3: rule__Table__XAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__Table__XAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getTableAccess().getXAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_4__1__Impl"


    // $ANTLR start "rule__Table__Group_5__0"
    // InternalDiningRoomTextual.g:865:1: rule__Table__Group_5__0 : rule__Table__Group_5__0__Impl rule__Table__Group_5__1 ;
    public final void rule__Table__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:869:1: ( rule__Table__Group_5__0__Impl rule__Table__Group_5__1 )
            // InternalDiningRoomTextual.g:870:2: rule__Table__Group_5__0__Impl rule__Table__Group_5__1
            {
            pushFollow(FOLLOW_12);
            rule__Table__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_5__0"


    // $ANTLR start "rule__Table__Group_5__0__Impl"
    // InternalDiningRoomTextual.g:877:1: rule__Table__Group_5__0__Impl : ( 'y' ) ;
    public final void rule__Table__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:881:1: ( ( 'y' ) )
            // InternalDiningRoomTextual.g:882:1: ( 'y' )
            {
            // InternalDiningRoomTextual.g:882:1: ( 'y' )
            // InternalDiningRoomTextual.g:883:2: 'y'
            {
             before(grammarAccess.getTableAccess().getYKeyword_5_0()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getYKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_5__0__Impl"


    // $ANTLR start "rule__Table__Group_5__1"
    // InternalDiningRoomTextual.g:892:1: rule__Table__Group_5__1 : rule__Table__Group_5__1__Impl ;
    public final void rule__Table__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:896:1: ( rule__Table__Group_5__1__Impl )
            // InternalDiningRoomTextual.g:897:2: rule__Table__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Table__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_5__1"


    // $ANTLR start "rule__Table__Group_5__1__Impl"
    // InternalDiningRoomTextual.g:903:1: rule__Table__Group_5__1__Impl : ( ( rule__Table__YAssignment_5_1 ) ) ;
    public final void rule__Table__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:907:1: ( ( ( rule__Table__YAssignment_5_1 ) ) )
            // InternalDiningRoomTextual.g:908:1: ( ( rule__Table__YAssignment_5_1 ) )
            {
            // InternalDiningRoomTextual.g:908:1: ( ( rule__Table__YAssignment_5_1 ) )
            // InternalDiningRoomTextual.g:909:2: ( rule__Table__YAssignment_5_1 )
            {
             before(grammarAccess.getTableAccess().getYAssignment_5_1()); 
            // InternalDiningRoomTextual.g:910:2: ( rule__Table__YAssignment_5_1 )
            // InternalDiningRoomTextual.g:910:3: rule__Table__YAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__Table__YAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getTableAccess().getYAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_5__1__Impl"


    // $ANTLR start "rule__Table__Group_6__0"
    // InternalDiningRoomTextual.g:919:1: rule__Table__Group_6__0 : rule__Table__Group_6__0__Impl rule__Table__Group_6__1 ;
    public final void rule__Table__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:923:1: ( rule__Table__Group_6__0__Impl rule__Table__Group_6__1 )
            // InternalDiningRoomTextual.g:924:2: rule__Table__Group_6__0__Impl rule__Table__Group_6__1
            {
            pushFollow(FOLLOW_13);
            rule__Table__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6__0"


    // $ANTLR start "rule__Table__Group_6__0__Impl"
    // InternalDiningRoomTextual.g:931:1: rule__Table__Group_6__0__Impl : ( 'around' ) ;
    public final void rule__Table__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:935:1: ( ( 'around' ) )
            // InternalDiningRoomTextual.g:936:1: ( 'around' )
            {
            // InternalDiningRoomTextual.g:936:1: ( 'around' )
            // InternalDiningRoomTextual.g:937:2: 'around'
            {
             before(grammarAccess.getTableAccess().getAroundKeyword_6_0()); 
            match(input,19,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getAroundKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6__0__Impl"


    // $ANTLR start "rule__Table__Group_6__1"
    // InternalDiningRoomTextual.g:946:1: rule__Table__Group_6__1 : rule__Table__Group_6__1__Impl rule__Table__Group_6__2 ;
    public final void rule__Table__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:950:1: ( rule__Table__Group_6__1__Impl rule__Table__Group_6__2 )
            // InternalDiningRoomTextual.g:951:2: rule__Table__Group_6__1__Impl rule__Table__Group_6__2
            {
            pushFollow(FOLLOW_4);
            rule__Table__Group_6__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group_6__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6__1"


    // $ANTLR start "rule__Table__Group_6__1__Impl"
    // InternalDiningRoomTextual.g:958:1: rule__Table__Group_6__1__Impl : ( '(' ) ;
    public final void rule__Table__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:962:1: ( ( '(' ) )
            // InternalDiningRoomTextual.g:963:1: ( '(' )
            {
            // InternalDiningRoomTextual.g:963:1: ( '(' )
            // InternalDiningRoomTextual.g:964:2: '('
            {
             before(grammarAccess.getTableAccess().getLeftParenthesisKeyword_6_1()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getLeftParenthesisKeyword_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6__1__Impl"


    // $ANTLR start "rule__Table__Group_6__2"
    // InternalDiningRoomTextual.g:973:1: rule__Table__Group_6__2 : rule__Table__Group_6__2__Impl rule__Table__Group_6__3 ;
    public final void rule__Table__Group_6__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:977:1: ( rule__Table__Group_6__2__Impl rule__Table__Group_6__3 )
            // InternalDiningRoomTextual.g:978:2: rule__Table__Group_6__2__Impl rule__Table__Group_6__3
            {
            pushFollow(FOLLOW_14);
            rule__Table__Group_6__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group_6__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6__2"


    // $ANTLR start "rule__Table__Group_6__2__Impl"
    // InternalDiningRoomTextual.g:985:1: rule__Table__Group_6__2__Impl : ( ( rule__Table__AroundAssignment_6_2 ) ) ;
    public final void rule__Table__Group_6__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:989:1: ( ( ( rule__Table__AroundAssignment_6_2 ) ) )
            // InternalDiningRoomTextual.g:990:1: ( ( rule__Table__AroundAssignment_6_2 ) )
            {
            // InternalDiningRoomTextual.g:990:1: ( ( rule__Table__AroundAssignment_6_2 ) )
            // InternalDiningRoomTextual.g:991:2: ( rule__Table__AroundAssignment_6_2 )
            {
             before(grammarAccess.getTableAccess().getAroundAssignment_6_2()); 
            // InternalDiningRoomTextual.g:992:2: ( rule__Table__AroundAssignment_6_2 )
            // InternalDiningRoomTextual.g:992:3: rule__Table__AroundAssignment_6_2
            {
            pushFollow(FOLLOW_2);
            rule__Table__AroundAssignment_6_2();

            state._fsp--;


            }

             after(grammarAccess.getTableAccess().getAroundAssignment_6_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6__2__Impl"


    // $ANTLR start "rule__Table__Group_6__3"
    // InternalDiningRoomTextual.g:1000:1: rule__Table__Group_6__3 : rule__Table__Group_6__3__Impl rule__Table__Group_6__4 ;
    public final void rule__Table__Group_6__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1004:1: ( rule__Table__Group_6__3__Impl rule__Table__Group_6__4 )
            // InternalDiningRoomTextual.g:1005:2: rule__Table__Group_6__3__Impl rule__Table__Group_6__4
            {
            pushFollow(FOLLOW_14);
            rule__Table__Group_6__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group_6__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6__3"


    // $ANTLR start "rule__Table__Group_6__3__Impl"
    // InternalDiningRoomTextual.g:1012:1: rule__Table__Group_6__3__Impl : ( ( rule__Table__Group_6_3__0 )* ) ;
    public final void rule__Table__Group_6__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1016:1: ( ( ( rule__Table__Group_6_3__0 )* ) )
            // InternalDiningRoomTextual.g:1017:1: ( ( rule__Table__Group_6_3__0 )* )
            {
            // InternalDiningRoomTextual.g:1017:1: ( ( rule__Table__Group_6_3__0 )* )
            // InternalDiningRoomTextual.g:1018:2: ( rule__Table__Group_6_3__0 )*
            {
             before(grammarAccess.getTableAccess().getGroup_6_3()); 
            // InternalDiningRoomTextual.g:1019:2: ( rule__Table__Group_6_3__0 )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==15) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalDiningRoomTextual.g:1019:3: rule__Table__Group_6_3__0
            	    {
            	    pushFollow(FOLLOW_9);
            	    rule__Table__Group_6_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

             after(grammarAccess.getTableAccess().getGroup_6_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6__3__Impl"


    // $ANTLR start "rule__Table__Group_6__4"
    // InternalDiningRoomTextual.g:1027:1: rule__Table__Group_6__4 : rule__Table__Group_6__4__Impl ;
    public final void rule__Table__Group_6__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1031:1: ( rule__Table__Group_6__4__Impl )
            // InternalDiningRoomTextual.g:1032:2: rule__Table__Group_6__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Table__Group_6__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6__4"


    // $ANTLR start "rule__Table__Group_6__4__Impl"
    // InternalDiningRoomTextual.g:1038:1: rule__Table__Group_6__4__Impl : ( ')' ) ;
    public final void rule__Table__Group_6__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1042:1: ( ( ')' ) )
            // InternalDiningRoomTextual.g:1043:1: ( ')' )
            {
            // InternalDiningRoomTextual.g:1043:1: ( ')' )
            // InternalDiningRoomTextual.g:1044:2: ')'
            {
             before(grammarAccess.getTableAccess().getRightParenthesisKeyword_6_4()); 
            match(input,21,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getRightParenthesisKeyword_6_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6__4__Impl"


    // $ANTLR start "rule__Table__Group_6_3__0"
    // InternalDiningRoomTextual.g:1054:1: rule__Table__Group_6_3__0 : rule__Table__Group_6_3__0__Impl rule__Table__Group_6_3__1 ;
    public final void rule__Table__Group_6_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1058:1: ( rule__Table__Group_6_3__0__Impl rule__Table__Group_6_3__1 )
            // InternalDiningRoomTextual.g:1059:2: rule__Table__Group_6_3__0__Impl rule__Table__Group_6_3__1
            {
            pushFollow(FOLLOW_4);
            rule__Table__Group_6_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group_6_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6_3__0"


    // $ANTLR start "rule__Table__Group_6_3__0__Impl"
    // InternalDiningRoomTextual.g:1066:1: rule__Table__Group_6_3__0__Impl : ( ',' ) ;
    public final void rule__Table__Group_6_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1070:1: ( ( ',' ) )
            // InternalDiningRoomTextual.g:1071:1: ( ',' )
            {
            // InternalDiningRoomTextual.g:1071:1: ( ',' )
            // InternalDiningRoomTextual.g:1072:2: ','
            {
             before(grammarAccess.getTableAccess().getCommaKeyword_6_3_0()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getCommaKeyword_6_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6_3__0__Impl"


    // $ANTLR start "rule__Table__Group_6_3__1"
    // InternalDiningRoomTextual.g:1081:1: rule__Table__Group_6_3__1 : rule__Table__Group_6_3__1__Impl ;
    public final void rule__Table__Group_6_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1085:1: ( rule__Table__Group_6_3__1__Impl )
            // InternalDiningRoomTextual.g:1086:2: rule__Table__Group_6_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Table__Group_6_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6_3__1"


    // $ANTLR start "rule__Table__Group_6_3__1__Impl"
    // InternalDiningRoomTextual.g:1092:1: rule__Table__Group_6_3__1__Impl : ( ( rule__Table__AroundAssignment_6_3_1 ) ) ;
    public final void rule__Table__Group_6_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1096:1: ( ( ( rule__Table__AroundAssignment_6_3_1 ) ) )
            // InternalDiningRoomTextual.g:1097:1: ( ( rule__Table__AroundAssignment_6_3_1 ) )
            {
            // InternalDiningRoomTextual.g:1097:1: ( ( rule__Table__AroundAssignment_6_3_1 ) )
            // InternalDiningRoomTextual.g:1098:2: ( rule__Table__AroundAssignment_6_3_1 )
            {
             before(grammarAccess.getTableAccess().getAroundAssignment_6_3_1()); 
            // InternalDiningRoomTextual.g:1099:2: ( rule__Table__AroundAssignment_6_3_1 )
            // InternalDiningRoomTextual.g:1099:3: rule__Table__AroundAssignment_6_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Table__AroundAssignment_6_3_1();

            state._fsp--;


            }

             after(grammarAccess.getTableAccess().getAroundAssignment_6_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_6_3__1__Impl"


    // $ANTLR start "rule__Chair__Group__0"
    // InternalDiningRoomTextual.g:1108:1: rule__Chair__Group__0 : rule__Chair__Group__0__Impl rule__Chair__Group__1 ;
    public final void rule__Chair__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1112:1: ( rule__Chair__Group__0__Impl rule__Chair__Group__1 )
            // InternalDiningRoomTextual.g:1113:2: rule__Chair__Group__0__Impl rule__Chair__Group__1
            {
            pushFollow(FOLLOW_7);
            rule__Chair__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Chair__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__0"


    // $ANTLR start "rule__Chair__Group__0__Impl"
    // InternalDiningRoomTextual.g:1120:1: rule__Chair__Group__0__Impl : ( () ) ;
    public final void rule__Chair__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1124:1: ( ( () ) )
            // InternalDiningRoomTextual.g:1125:1: ( () )
            {
            // InternalDiningRoomTextual.g:1125:1: ( () )
            // InternalDiningRoomTextual.g:1126:2: ()
            {
             before(grammarAccess.getChairAccess().getChairAction_0()); 
            // InternalDiningRoomTextual.g:1127:2: ()
            // InternalDiningRoomTextual.g:1127:3: 
            {
            }

             after(grammarAccess.getChairAccess().getChairAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__0__Impl"


    // $ANTLR start "rule__Chair__Group__1"
    // InternalDiningRoomTextual.g:1135:1: rule__Chair__Group__1 : rule__Chair__Group__1__Impl rule__Chair__Group__2 ;
    public final void rule__Chair__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1139:1: ( rule__Chair__Group__1__Impl rule__Chair__Group__2 )
            // InternalDiningRoomTextual.g:1140:2: rule__Chair__Group__1__Impl rule__Chair__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__Chair__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Chair__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__1"


    // $ANTLR start "rule__Chair__Group__1__Impl"
    // InternalDiningRoomTextual.g:1147:1: rule__Chair__Group__1__Impl : ( 'Chair' ) ;
    public final void rule__Chair__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1151:1: ( ( 'Chair' ) )
            // InternalDiningRoomTextual.g:1152:1: ( 'Chair' )
            {
            // InternalDiningRoomTextual.g:1152:1: ( 'Chair' )
            // InternalDiningRoomTextual.g:1153:2: 'Chair'
            {
             before(grammarAccess.getChairAccess().getChairKeyword_1()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getChairAccess().getChairKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__1__Impl"


    // $ANTLR start "rule__Chair__Group__2"
    // InternalDiningRoomTextual.g:1162:1: rule__Chair__Group__2 : rule__Chair__Group__2__Impl rule__Chair__Group__3 ;
    public final void rule__Chair__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1166:1: ( rule__Chair__Group__2__Impl rule__Chair__Group__3 )
            // InternalDiningRoomTextual.g:1167:2: rule__Chair__Group__2__Impl rule__Chair__Group__3
            {
            pushFollow(FOLLOW_5);
            rule__Chair__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Chair__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__2"


    // $ANTLR start "rule__Chair__Group__2__Impl"
    // InternalDiningRoomTextual.g:1174:1: rule__Chair__Group__2__Impl : ( ( rule__Chair__NameAssignment_2 ) ) ;
    public final void rule__Chair__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1178:1: ( ( ( rule__Chair__NameAssignment_2 ) ) )
            // InternalDiningRoomTextual.g:1179:1: ( ( rule__Chair__NameAssignment_2 ) )
            {
            // InternalDiningRoomTextual.g:1179:1: ( ( rule__Chair__NameAssignment_2 ) )
            // InternalDiningRoomTextual.g:1180:2: ( rule__Chair__NameAssignment_2 )
            {
             before(grammarAccess.getChairAccess().getNameAssignment_2()); 
            // InternalDiningRoomTextual.g:1181:2: ( rule__Chair__NameAssignment_2 )
            // InternalDiningRoomTextual.g:1181:3: rule__Chair__NameAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Chair__NameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getChairAccess().getNameAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__2__Impl"


    // $ANTLR start "rule__Chair__Group__3"
    // InternalDiningRoomTextual.g:1189:1: rule__Chair__Group__3 : rule__Chair__Group__3__Impl rule__Chair__Group__4 ;
    public final void rule__Chair__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1193:1: ( rule__Chair__Group__3__Impl rule__Chair__Group__4 )
            // InternalDiningRoomTextual.g:1194:2: rule__Chair__Group__3__Impl rule__Chair__Group__4
            {
            pushFollow(FOLLOW_15);
            rule__Chair__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Chair__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__3"


    // $ANTLR start "rule__Chair__Group__3__Impl"
    // InternalDiningRoomTextual.g:1201:1: rule__Chair__Group__3__Impl : ( '{' ) ;
    public final void rule__Chair__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1205:1: ( ( '{' ) )
            // InternalDiningRoomTextual.g:1206:1: ( '{' )
            {
            // InternalDiningRoomTextual.g:1206:1: ( '{' )
            // InternalDiningRoomTextual.g:1207:2: '{'
            {
             before(grammarAccess.getChairAccess().getLeftCurlyBracketKeyword_3()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getChairAccess().getLeftCurlyBracketKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__3__Impl"


    // $ANTLR start "rule__Chair__Group__4"
    // InternalDiningRoomTextual.g:1216:1: rule__Chair__Group__4 : rule__Chair__Group__4__Impl rule__Chair__Group__5 ;
    public final void rule__Chair__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1220:1: ( rule__Chair__Group__4__Impl rule__Chair__Group__5 )
            // InternalDiningRoomTextual.g:1221:2: rule__Chair__Group__4__Impl rule__Chair__Group__5
            {
            pushFollow(FOLLOW_15);
            rule__Chair__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Chair__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__4"


    // $ANTLR start "rule__Chair__Group__4__Impl"
    // InternalDiningRoomTextual.g:1228:1: rule__Chair__Group__4__Impl : ( ( rule__Chair__Group_4__0 )? ) ;
    public final void rule__Chair__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1232:1: ( ( ( rule__Chair__Group_4__0 )? ) )
            // InternalDiningRoomTextual.g:1233:1: ( ( rule__Chair__Group_4__0 )? )
            {
            // InternalDiningRoomTextual.g:1233:1: ( ( rule__Chair__Group_4__0 )? )
            // InternalDiningRoomTextual.g:1234:2: ( rule__Chair__Group_4__0 )?
            {
             before(grammarAccess.getChairAccess().getGroup_4()); 
            // InternalDiningRoomTextual.g:1235:2: ( rule__Chair__Group_4__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==23) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalDiningRoomTextual.g:1235:3: rule__Chair__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Chair__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getChairAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__4__Impl"


    // $ANTLR start "rule__Chair__Group__5"
    // InternalDiningRoomTextual.g:1243:1: rule__Chair__Group__5 : rule__Chair__Group__5__Impl ;
    public final void rule__Chair__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1247:1: ( rule__Chair__Group__5__Impl )
            // InternalDiningRoomTextual.g:1248:2: rule__Chair__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Chair__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__5"


    // $ANTLR start "rule__Chair__Group__5__Impl"
    // InternalDiningRoomTextual.g:1254:1: rule__Chair__Group__5__Impl : ( '}' ) ;
    public final void rule__Chair__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1258:1: ( ( '}' ) )
            // InternalDiningRoomTextual.g:1259:1: ( '}' )
            {
            // InternalDiningRoomTextual.g:1259:1: ( '}' )
            // InternalDiningRoomTextual.g:1260:2: '}'
            {
             before(grammarAccess.getChairAccess().getRightCurlyBracketKeyword_5()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getChairAccess().getRightCurlyBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group__5__Impl"


    // $ANTLR start "rule__Chair__Group_4__0"
    // InternalDiningRoomTextual.g:1270:1: rule__Chair__Group_4__0 : rule__Chair__Group_4__0__Impl rule__Chair__Group_4__1 ;
    public final void rule__Chair__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1274:1: ( rule__Chair__Group_4__0__Impl rule__Chair__Group_4__1 )
            // InternalDiningRoomTextual.g:1275:2: rule__Chair__Group_4__0__Impl rule__Chair__Group_4__1
            {
            pushFollow(FOLLOW_12);
            rule__Chair__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Chair__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group_4__0"


    // $ANTLR start "rule__Chair__Group_4__0__Impl"
    // InternalDiningRoomTextual.g:1282:1: rule__Chair__Group_4__0__Impl : ( 'order' ) ;
    public final void rule__Chair__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1286:1: ( ( 'order' ) )
            // InternalDiningRoomTextual.g:1287:1: ( 'order' )
            {
            // InternalDiningRoomTextual.g:1287:1: ( 'order' )
            // InternalDiningRoomTextual.g:1288:2: 'order'
            {
             before(grammarAccess.getChairAccess().getOrderKeyword_4_0()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getChairAccess().getOrderKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group_4__0__Impl"


    // $ANTLR start "rule__Chair__Group_4__1"
    // InternalDiningRoomTextual.g:1297:1: rule__Chair__Group_4__1 : rule__Chair__Group_4__1__Impl ;
    public final void rule__Chair__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1301:1: ( rule__Chair__Group_4__1__Impl )
            // InternalDiningRoomTextual.g:1302:2: rule__Chair__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Chair__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group_4__1"


    // $ANTLR start "rule__Chair__Group_4__1__Impl"
    // InternalDiningRoomTextual.g:1308:1: rule__Chair__Group_4__1__Impl : ( ( rule__Chair__OrderAssignment_4_1 ) ) ;
    public final void rule__Chair__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1312:1: ( ( ( rule__Chair__OrderAssignment_4_1 ) ) )
            // InternalDiningRoomTextual.g:1313:1: ( ( rule__Chair__OrderAssignment_4_1 ) )
            {
            // InternalDiningRoomTextual.g:1313:1: ( ( rule__Chair__OrderAssignment_4_1 ) )
            // InternalDiningRoomTextual.g:1314:2: ( rule__Chair__OrderAssignment_4_1 )
            {
             before(grammarAccess.getChairAccess().getOrderAssignment_4_1()); 
            // InternalDiningRoomTextual.g:1315:2: ( rule__Chair__OrderAssignment_4_1 )
            // InternalDiningRoomTextual.g:1315:3: rule__Chair__OrderAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__Chair__OrderAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getChairAccess().getOrderAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__Group_4__1__Impl"


    // $ANTLR start "rule__EInt__Group__0"
    // InternalDiningRoomTextual.g:1324:1: rule__EInt__Group__0 : rule__EInt__Group__0__Impl rule__EInt__Group__1 ;
    public final void rule__EInt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1328:1: ( rule__EInt__Group__0__Impl rule__EInt__Group__1 )
            // InternalDiningRoomTextual.g:1329:2: rule__EInt__Group__0__Impl rule__EInt__Group__1
            {
            pushFollow(FOLLOW_12);
            rule__EInt__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EInt__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EInt__Group__0"


    // $ANTLR start "rule__EInt__Group__0__Impl"
    // InternalDiningRoomTextual.g:1336:1: rule__EInt__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__EInt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1340:1: ( ( ( '-' )? ) )
            // InternalDiningRoomTextual.g:1341:1: ( ( '-' )? )
            {
            // InternalDiningRoomTextual.g:1341:1: ( ( '-' )? )
            // InternalDiningRoomTextual.g:1342:2: ( '-' )?
            {
             before(grammarAccess.getEIntAccess().getHyphenMinusKeyword_0()); 
            // InternalDiningRoomTextual.g:1343:2: ( '-' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==24) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalDiningRoomTextual.g:1343:3: '-'
                    {
                    match(input,24,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getEIntAccess().getHyphenMinusKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EInt__Group__0__Impl"


    // $ANTLR start "rule__EInt__Group__1"
    // InternalDiningRoomTextual.g:1351:1: rule__EInt__Group__1 : rule__EInt__Group__1__Impl ;
    public final void rule__EInt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1355:1: ( rule__EInt__Group__1__Impl )
            // InternalDiningRoomTextual.g:1356:2: rule__EInt__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__EInt__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EInt__Group__1"


    // $ANTLR start "rule__EInt__Group__1__Impl"
    // InternalDiningRoomTextual.g:1362:1: rule__EInt__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__EInt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1366:1: ( ( RULE_INT ) )
            // InternalDiningRoomTextual.g:1367:1: ( RULE_INT )
            {
            // InternalDiningRoomTextual.g:1367:1: ( RULE_INT )
            // InternalDiningRoomTextual.g:1368:2: RULE_INT
            {
             before(grammarAccess.getEIntAccess().getINTTerminalRuleCall_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getEIntAccess().getINTTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EInt__Group__1__Impl"


    // $ANTLR start "rule__Room__NameAssignment_2"
    // InternalDiningRoomTextual.g:1378:1: rule__Room__NameAssignment_2 : ( ruleEString ) ;
    public final void rule__Room__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1382:1: ( ( ruleEString ) )
            // InternalDiningRoomTextual.g:1383:2: ( ruleEString )
            {
            // InternalDiningRoomTextual.g:1383:2: ( ruleEString )
            // InternalDiningRoomTextual.g:1384:3: ruleEString
            {
             before(grammarAccess.getRoomAccess().getNameEStringParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getRoomAccess().getNameEStringParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__NameAssignment_2"


    // $ANTLR start "rule__Room__FurnitureAssignment_4_2"
    // InternalDiningRoomTextual.g:1393:1: rule__Room__FurnitureAssignment_4_2 : ( ruleFurniture ) ;
    public final void rule__Room__FurnitureAssignment_4_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1397:1: ( ( ruleFurniture ) )
            // InternalDiningRoomTextual.g:1398:2: ( ruleFurniture )
            {
            // InternalDiningRoomTextual.g:1398:2: ( ruleFurniture )
            // InternalDiningRoomTextual.g:1399:3: ruleFurniture
            {
             before(grammarAccess.getRoomAccess().getFurnitureFurnitureParserRuleCall_4_2_0()); 
            pushFollow(FOLLOW_2);
            ruleFurniture();

            state._fsp--;

             after(grammarAccess.getRoomAccess().getFurnitureFurnitureParserRuleCall_4_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__FurnitureAssignment_4_2"


    // $ANTLR start "rule__Room__FurnitureAssignment_4_3_1"
    // InternalDiningRoomTextual.g:1408:1: rule__Room__FurnitureAssignment_4_3_1 : ( ruleFurniture ) ;
    public final void rule__Room__FurnitureAssignment_4_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1412:1: ( ( ruleFurniture ) )
            // InternalDiningRoomTextual.g:1413:2: ( ruleFurniture )
            {
            // InternalDiningRoomTextual.g:1413:2: ( ruleFurniture )
            // InternalDiningRoomTextual.g:1414:3: ruleFurniture
            {
             before(grammarAccess.getRoomAccess().getFurnitureFurnitureParserRuleCall_4_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleFurniture();

            state._fsp--;

             after(grammarAccess.getRoomAccess().getFurnitureFurnitureParserRuleCall_4_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Room__FurnitureAssignment_4_3_1"


    // $ANTLR start "rule__Table__NameAssignment_2"
    // InternalDiningRoomTextual.g:1423:1: rule__Table__NameAssignment_2 : ( ruleEString ) ;
    public final void rule__Table__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1427:1: ( ( ruleEString ) )
            // InternalDiningRoomTextual.g:1428:2: ( ruleEString )
            {
            // InternalDiningRoomTextual.g:1428:2: ( ruleEString )
            // InternalDiningRoomTextual.g:1429:3: ruleEString
            {
             before(grammarAccess.getTableAccess().getNameEStringParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getTableAccess().getNameEStringParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__NameAssignment_2"


    // $ANTLR start "rule__Table__XAssignment_4_1"
    // InternalDiningRoomTextual.g:1438:1: rule__Table__XAssignment_4_1 : ( ruleEInt ) ;
    public final void rule__Table__XAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1442:1: ( ( ruleEInt ) )
            // InternalDiningRoomTextual.g:1443:2: ( ruleEInt )
            {
            // InternalDiningRoomTextual.g:1443:2: ( ruleEInt )
            // InternalDiningRoomTextual.g:1444:3: ruleEInt
            {
             before(grammarAccess.getTableAccess().getXEIntParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_2);
            ruleEInt();

            state._fsp--;

             after(grammarAccess.getTableAccess().getXEIntParserRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__XAssignment_4_1"


    // $ANTLR start "rule__Table__YAssignment_5_1"
    // InternalDiningRoomTextual.g:1453:1: rule__Table__YAssignment_5_1 : ( ruleEInt ) ;
    public final void rule__Table__YAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1457:1: ( ( ruleEInt ) )
            // InternalDiningRoomTextual.g:1458:2: ( ruleEInt )
            {
            // InternalDiningRoomTextual.g:1458:2: ( ruleEInt )
            // InternalDiningRoomTextual.g:1459:3: ruleEInt
            {
             before(grammarAccess.getTableAccess().getYEIntParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_2);
            ruleEInt();

            state._fsp--;

             after(grammarAccess.getTableAccess().getYEIntParserRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__YAssignment_5_1"


    // $ANTLR start "rule__Table__AroundAssignment_6_2"
    // InternalDiningRoomTextual.g:1468:1: rule__Table__AroundAssignment_6_2 : ( ( ruleEString ) ) ;
    public final void rule__Table__AroundAssignment_6_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1472:1: ( ( ( ruleEString ) ) )
            // InternalDiningRoomTextual.g:1473:2: ( ( ruleEString ) )
            {
            // InternalDiningRoomTextual.g:1473:2: ( ( ruleEString ) )
            // InternalDiningRoomTextual.g:1474:3: ( ruleEString )
            {
             before(grammarAccess.getTableAccess().getAroundChairCrossReference_6_2_0()); 
            // InternalDiningRoomTextual.g:1475:3: ( ruleEString )
            // InternalDiningRoomTextual.g:1476:4: ruleEString
            {
             before(grammarAccess.getTableAccess().getAroundChairEStringParserRuleCall_6_2_0_1()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getTableAccess().getAroundChairEStringParserRuleCall_6_2_0_1()); 

            }

             after(grammarAccess.getTableAccess().getAroundChairCrossReference_6_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__AroundAssignment_6_2"


    // $ANTLR start "rule__Table__AroundAssignment_6_3_1"
    // InternalDiningRoomTextual.g:1487:1: rule__Table__AroundAssignment_6_3_1 : ( ( ruleEString ) ) ;
    public final void rule__Table__AroundAssignment_6_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1491:1: ( ( ( ruleEString ) ) )
            // InternalDiningRoomTextual.g:1492:2: ( ( ruleEString ) )
            {
            // InternalDiningRoomTextual.g:1492:2: ( ( ruleEString ) )
            // InternalDiningRoomTextual.g:1493:3: ( ruleEString )
            {
             before(grammarAccess.getTableAccess().getAroundChairCrossReference_6_3_1_0()); 
            // InternalDiningRoomTextual.g:1494:3: ( ruleEString )
            // InternalDiningRoomTextual.g:1495:4: ruleEString
            {
             before(grammarAccess.getTableAccess().getAroundChairEStringParserRuleCall_6_3_1_0_1()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getTableAccess().getAroundChairEStringParserRuleCall_6_3_1_0_1()); 

            }

             after(grammarAccess.getTableAccess().getAroundChairCrossReference_6_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__AroundAssignment_6_3_1"


    // $ANTLR start "rule__Chair__NameAssignment_2"
    // InternalDiningRoomTextual.g:1506:1: rule__Chair__NameAssignment_2 : ( ruleEString ) ;
    public final void rule__Chair__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1510:1: ( ( ruleEString ) )
            // InternalDiningRoomTextual.g:1511:2: ( ruleEString )
            {
            // InternalDiningRoomTextual.g:1511:2: ( ruleEString )
            // InternalDiningRoomTextual.g:1512:3: ruleEString
            {
             before(grammarAccess.getChairAccess().getNameEStringParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getChairAccess().getNameEStringParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__NameAssignment_2"


    // $ANTLR start "rule__Chair__OrderAssignment_4_1"
    // InternalDiningRoomTextual.g:1521:1: rule__Chair__OrderAssignment_4_1 : ( ruleEInt ) ;
    public final void rule__Chair__OrderAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalDiningRoomTextual.g:1525:1: ( ( ruleEInt ) )
            // InternalDiningRoomTextual.g:1526:2: ( ruleEInt )
            {
            // InternalDiningRoomTextual.g:1526:2: ( ruleEInt )
            // InternalDiningRoomTextual.g:1527:3: ruleEInt
            {
             before(grammarAccess.getChairAccess().getOrderEIntParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_2);
            ruleEInt();

            state._fsp--;

             after(grammarAccess.getChairAccess().getOrderEIntParserRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Chair__OrderAssignment_4_1"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000006000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000410000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x000000000000A000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x00000000000E2000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000001000040L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000208000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000802000L});

}