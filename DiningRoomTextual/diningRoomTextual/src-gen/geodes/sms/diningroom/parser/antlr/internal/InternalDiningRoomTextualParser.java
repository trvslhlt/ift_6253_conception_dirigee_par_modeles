package geodes.sms.diningroom.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import geodes.sms.diningroom.services.DiningRoomTextualGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalDiningRoomTextualParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'Room'", "'{'", "'furniture'", "','", "'}'", "'Table'", "'x'", "'y'", "'around'", "'('", "')'", "'Chair'", "'order'", "'-'"
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

        public InternalDiningRoomTextualParser(TokenStream input, DiningRoomTextualGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Room";
       	}

       	@Override
       	protected DiningRoomTextualGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleRoom"
    // InternalDiningRoomTextual.g:64:1: entryRuleRoom returns [EObject current=null] : iv_ruleRoom= ruleRoom EOF ;
    public final EObject entryRuleRoom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoom = null;


        try {
            // InternalDiningRoomTextual.g:64:45: (iv_ruleRoom= ruleRoom EOF )
            // InternalDiningRoomTextual.g:65:2: iv_ruleRoom= ruleRoom EOF
            {
             newCompositeNode(grammarAccess.getRoomRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRoom=ruleRoom();

            state._fsp--;

             current =iv_ruleRoom; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRoom"


    // $ANTLR start "ruleRoom"
    // InternalDiningRoomTextual.g:71:1: ruleRoom returns [EObject current=null] : ( () otherlv_1= 'Room' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'furniture' otherlv_5= '{' ( (lv_furniture_6_0= ruleFurniture ) ) (otherlv_7= ',' ( (lv_furniture_8_0= ruleFurniture ) ) )* otherlv_9= '}' )? otherlv_10= '}' ) ;
    public final EObject ruleRoom() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_furniture_6_0 = null;

        EObject lv_furniture_8_0 = null;



        	enterRule();

        try {
            // InternalDiningRoomTextual.g:77:2: ( ( () otherlv_1= 'Room' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'furniture' otherlv_5= '{' ( (lv_furniture_6_0= ruleFurniture ) ) (otherlv_7= ',' ( (lv_furniture_8_0= ruleFurniture ) ) )* otherlv_9= '}' )? otherlv_10= '}' ) )
            // InternalDiningRoomTextual.g:78:2: ( () otherlv_1= 'Room' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'furniture' otherlv_5= '{' ( (lv_furniture_6_0= ruleFurniture ) ) (otherlv_7= ',' ( (lv_furniture_8_0= ruleFurniture ) ) )* otherlv_9= '}' )? otherlv_10= '}' )
            {
            // InternalDiningRoomTextual.g:78:2: ( () otherlv_1= 'Room' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'furniture' otherlv_5= '{' ( (lv_furniture_6_0= ruleFurniture ) ) (otherlv_7= ',' ( (lv_furniture_8_0= ruleFurniture ) ) )* otherlv_9= '}' )? otherlv_10= '}' )
            // InternalDiningRoomTextual.g:79:3: () otherlv_1= 'Room' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'furniture' otherlv_5= '{' ( (lv_furniture_6_0= ruleFurniture ) ) (otherlv_7= ',' ( (lv_furniture_8_0= ruleFurniture ) ) )* otherlv_9= '}' )? otherlv_10= '}'
            {
            // InternalDiningRoomTextual.g:79:3: ()
            // InternalDiningRoomTextual.g:80:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getRoomAccess().getRoomAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,11,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getRoomAccess().getRoomKeyword_1());
            		
            // InternalDiningRoomTextual.g:90:3: ( (lv_name_2_0= ruleEString ) )
            // InternalDiningRoomTextual.g:91:4: (lv_name_2_0= ruleEString )
            {
            // InternalDiningRoomTextual.g:91:4: (lv_name_2_0= ruleEString )
            // InternalDiningRoomTextual.g:92:5: lv_name_2_0= ruleEString
            {

            					newCompositeNode(grammarAccess.getRoomAccess().getNameEStringParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_4);
            lv_name_2_0=ruleEString();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRoomRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_2_0,
            						"geodes.sms.diningroom.DiningRoomTextual.EString");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_3, grammarAccess.getRoomAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalDiningRoomTextual.g:113:3: (otherlv_4= 'furniture' otherlv_5= '{' ( (lv_furniture_6_0= ruleFurniture ) ) (otherlv_7= ',' ( (lv_furniture_8_0= ruleFurniture ) ) )* otherlv_9= '}' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==13) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalDiningRoomTextual.g:114:4: otherlv_4= 'furniture' otherlv_5= '{' ( (lv_furniture_6_0= ruleFurniture ) ) (otherlv_7= ',' ( (lv_furniture_8_0= ruleFurniture ) ) )* otherlv_9= '}'
                    {
                    otherlv_4=(Token)match(input,13,FOLLOW_4); 

                    				newLeafNode(otherlv_4, grammarAccess.getRoomAccess().getFurnitureKeyword_4_0());
                    			
                    otherlv_5=(Token)match(input,12,FOLLOW_6); 

                    				newLeafNode(otherlv_5, grammarAccess.getRoomAccess().getLeftCurlyBracketKeyword_4_1());
                    			
                    // InternalDiningRoomTextual.g:122:4: ( (lv_furniture_6_0= ruleFurniture ) )
                    // InternalDiningRoomTextual.g:123:5: (lv_furniture_6_0= ruleFurniture )
                    {
                    // InternalDiningRoomTextual.g:123:5: (lv_furniture_6_0= ruleFurniture )
                    // InternalDiningRoomTextual.g:124:6: lv_furniture_6_0= ruleFurniture
                    {

                    						newCompositeNode(grammarAccess.getRoomAccess().getFurnitureFurnitureParserRuleCall_4_2_0());
                    					
                    pushFollow(FOLLOW_7);
                    lv_furniture_6_0=ruleFurniture();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRoomRule());
                    						}
                    						add(
                    							current,
                    							"furniture",
                    							lv_furniture_6_0,
                    							"geodes.sms.diningroom.DiningRoomTextual.Furniture");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalDiningRoomTextual.g:141:4: (otherlv_7= ',' ( (lv_furniture_8_0= ruleFurniture ) ) )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==14) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // InternalDiningRoomTextual.g:142:5: otherlv_7= ',' ( (lv_furniture_8_0= ruleFurniture ) )
                    	    {
                    	    otherlv_7=(Token)match(input,14,FOLLOW_6); 

                    	    					newLeafNode(otherlv_7, grammarAccess.getRoomAccess().getCommaKeyword_4_3_0());
                    	    				
                    	    // InternalDiningRoomTextual.g:146:5: ( (lv_furniture_8_0= ruleFurniture ) )
                    	    // InternalDiningRoomTextual.g:147:6: (lv_furniture_8_0= ruleFurniture )
                    	    {
                    	    // InternalDiningRoomTextual.g:147:6: (lv_furniture_8_0= ruleFurniture )
                    	    // InternalDiningRoomTextual.g:148:7: lv_furniture_8_0= ruleFurniture
                    	    {

                    	    							newCompositeNode(grammarAccess.getRoomAccess().getFurnitureFurnitureParserRuleCall_4_3_1_0());
                    	    						
                    	    pushFollow(FOLLOW_7);
                    	    lv_furniture_8_0=ruleFurniture();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getRoomRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"furniture",
                    	    								lv_furniture_8_0,
                    	    								"geodes.sms.diningroom.DiningRoomTextual.Furniture");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                    otherlv_9=(Token)match(input,15,FOLLOW_8); 

                    				newLeafNode(otherlv_9, grammarAccess.getRoomAccess().getRightCurlyBracketKeyword_4_4());
                    			

                    }
                    break;

            }

            otherlv_10=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_10, grammarAccess.getRoomAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRoom"


    // $ANTLR start "entryRuleFurniture"
    // InternalDiningRoomTextual.g:179:1: entryRuleFurniture returns [EObject current=null] : iv_ruleFurniture= ruleFurniture EOF ;
    public final EObject entryRuleFurniture() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFurniture = null;


        try {
            // InternalDiningRoomTextual.g:179:50: (iv_ruleFurniture= ruleFurniture EOF )
            // InternalDiningRoomTextual.g:180:2: iv_ruleFurniture= ruleFurniture EOF
            {
             newCompositeNode(grammarAccess.getFurnitureRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFurniture=ruleFurniture();

            state._fsp--;

             current =iv_ruleFurniture; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFurniture"


    // $ANTLR start "ruleFurniture"
    // InternalDiningRoomTextual.g:186:1: ruleFurniture returns [EObject current=null] : (this_Table_0= ruleTable | this_Chair_1= ruleChair ) ;
    public final EObject ruleFurniture() throws RecognitionException {
        EObject current = null;

        EObject this_Table_0 = null;

        EObject this_Chair_1 = null;



        	enterRule();

        try {
            // InternalDiningRoomTextual.g:192:2: ( (this_Table_0= ruleTable | this_Chair_1= ruleChair ) )
            // InternalDiningRoomTextual.g:193:2: (this_Table_0= ruleTable | this_Chair_1= ruleChair )
            {
            // InternalDiningRoomTextual.g:193:2: (this_Table_0= ruleTable | this_Chair_1= ruleChair )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==16) ) {
                alt3=1;
            }
            else if ( (LA3_0==22) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalDiningRoomTextual.g:194:3: this_Table_0= ruleTable
                    {

                    			newCompositeNode(grammarAccess.getFurnitureAccess().getTableParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Table_0=ruleTable();

                    state._fsp--;


                    			current = this_Table_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalDiningRoomTextual.g:203:3: this_Chair_1= ruleChair
                    {

                    			newCompositeNode(grammarAccess.getFurnitureAccess().getChairParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Chair_1=ruleChair();

                    state._fsp--;


                    			current = this_Chair_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFurniture"


    // $ANTLR start "entryRuleEString"
    // InternalDiningRoomTextual.g:215:1: entryRuleEString returns [String current=null] : iv_ruleEString= ruleEString EOF ;
    public final String entryRuleEString() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEString = null;


        try {
            // InternalDiningRoomTextual.g:215:47: (iv_ruleEString= ruleEString EOF )
            // InternalDiningRoomTextual.g:216:2: iv_ruleEString= ruleEString EOF
            {
             newCompositeNode(grammarAccess.getEStringRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEString=ruleEString();

            state._fsp--;

             current =iv_ruleEString.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEString"


    // $ANTLR start "ruleEString"
    // InternalDiningRoomTextual.g:222:1: ruleEString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID ) ;
    public final AntlrDatatypeRuleToken ruleEString() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;
        Token this_ID_1=null;


        	enterRule();

        try {
            // InternalDiningRoomTextual.g:228:2: ( (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID ) )
            // InternalDiningRoomTextual.g:229:2: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID )
            {
            // InternalDiningRoomTextual.g:229:2: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RULE_STRING) ) {
                alt4=1;
            }
            else if ( (LA4_0==RULE_ID) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // InternalDiningRoomTextual.g:230:3: this_STRING_0= RULE_STRING
                    {
                    this_STRING_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    			current.merge(this_STRING_0);
                    		

                    			newLeafNode(this_STRING_0, grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalDiningRoomTextual.g:238:3: this_ID_1= RULE_ID
                    {
                    this_ID_1=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_1);
                    		

                    			newLeafNode(this_ID_1, grammarAccess.getEStringAccess().getIDTerminalRuleCall_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEString"


    // $ANTLR start "entryRuleTable"
    // InternalDiningRoomTextual.g:249:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalDiningRoomTextual.g:249:46: (iv_ruleTable= ruleTable EOF )
            // InternalDiningRoomTextual.g:250:2: iv_ruleTable= ruleTable EOF
            {
             newCompositeNode(grammarAccess.getTableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTable=ruleTable();

            state._fsp--;

             current =iv_ruleTable; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTable"


    // $ANTLR start "ruleTable"
    // InternalDiningRoomTextual.g:256:1: ruleTable returns [EObject current=null] : ( () otherlv_1= 'Table' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'x' ( (lv_x_5_0= ruleEInt ) ) )? (otherlv_6= 'y' ( (lv_y_7_0= ruleEInt ) ) )? (otherlv_8= 'around' otherlv_9= '(' ( ( ruleEString ) ) (otherlv_11= ',' ( ( ruleEString ) ) )* otherlv_13= ')' )? otherlv_14= '}' ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        AntlrDatatypeRuleToken lv_x_5_0 = null;

        AntlrDatatypeRuleToken lv_y_7_0 = null;



        	enterRule();

        try {
            // InternalDiningRoomTextual.g:262:2: ( ( () otherlv_1= 'Table' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'x' ( (lv_x_5_0= ruleEInt ) ) )? (otherlv_6= 'y' ( (lv_y_7_0= ruleEInt ) ) )? (otherlv_8= 'around' otherlv_9= '(' ( ( ruleEString ) ) (otherlv_11= ',' ( ( ruleEString ) ) )* otherlv_13= ')' )? otherlv_14= '}' ) )
            // InternalDiningRoomTextual.g:263:2: ( () otherlv_1= 'Table' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'x' ( (lv_x_5_0= ruleEInt ) ) )? (otherlv_6= 'y' ( (lv_y_7_0= ruleEInt ) ) )? (otherlv_8= 'around' otherlv_9= '(' ( ( ruleEString ) ) (otherlv_11= ',' ( ( ruleEString ) ) )* otherlv_13= ')' )? otherlv_14= '}' )
            {
            // InternalDiningRoomTextual.g:263:2: ( () otherlv_1= 'Table' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'x' ( (lv_x_5_0= ruleEInt ) ) )? (otherlv_6= 'y' ( (lv_y_7_0= ruleEInt ) ) )? (otherlv_8= 'around' otherlv_9= '(' ( ( ruleEString ) ) (otherlv_11= ',' ( ( ruleEString ) ) )* otherlv_13= ')' )? otherlv_14= '}' )
            // InternalDiningRoomTextual.g:264:3: () otherlv_1= 'Table' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'x' ( (lv_x_5_0= ruleEInt ) ) )? (otherlv_6= 'y' ( (lv_y_7_0= ruleEInt ) ) )? (otherlv_8= 'around' otherlv_9= '(' ( ( ruleEString ) ) (otherlv_11= ',' ( ( ruleEString ) ) )* otherlv_13= ')' )? otherlv_14= '}'
            {
            // InternalDiningRoomTextual.g:264:3: ()
            // InternalDiningRoomTextual.g:265:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getTableAccess().getTableAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,16,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getTableAccess().getTableKeyword_1());
            		
            // InternalDiningRoomTextual.g:275:3: ( (lv_name_2_0= ruleEString ) )
            // InternalDiningRoomTextual.g:276:4: (lv_name_2_0= ruleEString )
            {
            // InternalDiningRoomTextual.g:276:4: (lv_name_2_0= ruleEString )
            // InternalDiningRoomTextual.g:277:5: lv_name_2_0= ruleEString
            {

            					newCompositeNode(grammarAccess.getTableAccess().getNameEStringParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_4);
            lv_name_2_0=ruleEString();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTableRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_2_0,
            						"geodes.sms.diningroom.DiningRoomTextual.EString");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,12,FOLLOW_9); 

            			newLeafNode(otherlv_3, grammarAccess.getTableAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalDiningRoomTextual.g:298:3: (otherlv_4= 'x' ( (lv_x_5_0= ruleEInt ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==17) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalDiningRoomTextual.g:299:4: otherlv_4= 'x' ( (lv_x_5_0= ruleEInt ) )
                    {
                    otherlv_4=(Token)match(input,17,FOLLOW_10); 

                    				newLeafNode(otherlv_4, grammarAccess.getTableAccess().getXKeyword_4_0());
                    			
                    // InternalDiningRoomTextual.g:303:4: ( (lv_x_5_0= ruleEInt ) )
                    // InternalDiningRoomTextual.g:304:5: (lv_x_5_0= ruleEInt )
                    {
                    // InternalDiningRoomTextual.g:304:5: (lv_x_5_0= ruleEInt )
                    // InternalDiningRoomTextual.g:305:6: lv_x_5_0= ruleEInt
                    {

                    						newCompositeNode(grammarAccess.getTableAccess().getXEIntParserRuleCall_4_1_0());
                    					
                    pushFollow(FOLLOW_11);
                    lv_x_5_0=ruleEInt();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTableRule());
                    						}
                    						set(
                    							current,
                    							"x",
                    							lv_x_5_0,
                    							"geodes.sms.diningroom.DiningRoomTextual.EInt");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalDiningRoomTextual.g:323:3: (otherlv_6= 'y' ( (lv_y_7_0= ruleEInt ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==18) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalDiningRoomTextual.g:324:4: otherlv_6= 'y' ( (lv_y_7_0= ruleEInt ) )
                    {
                    otherlv_6=(Token)match(input,18,FOLLOW_10); 

                    				newLeafNode(otherlv_6, grammarAccess.getTableAccess().getYKeyword_5_0());
                    			
                    // InternalDiningRoomTextual.g:328:4: ( (lv_y_7_0= ruleEInt ) )
                    // InternalDiningRoomTextual.g:329:5: (lv_y_7_0= ruleEInt )
                    {
                    // InternalDiningRoomTextual.g:329:5: (lv_y_7_0= ruleEInt )
                    // InternalDiningRoomTextual.g:330:6: lv_y_7_0= ruleEInt
                    {

                    						newCompositeNode(grammarAccess.getTableAccess().getYEIntParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_12);
                    lv_y_7_0=ruleEInt();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTableRule());
                    						}
                    						set(
                    							current,
                    							"y",
                    							lv_y_7_0,
                    							"geodes.sms.diningroom.DiningRoomTextual.EInt");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalDiningRoomTextual.g:348:3: (otherlv_8= 'around' otherlv_9= '(' ( ( ruleEString ) ) (otherlv_11= ',' ( ( ruleEString ) ) )* otherlv_13= ')' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==19) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalDiningRoomTextual.g:349:4: otherlv_8= 'around' otherlv_9= '(' ( ( ruleEString ) ) (otherlv_11= ',' ( ( ruleEString ) ) )* otherlv_13= ')'
                    {
                    otherlv_8=(Token)match(input,19,FOLLOW_13); 

                    				newLeafNode(otherlv_8, grammarAccess.getTableAccess().getAroundKeyword_6_0());
                    			
                    otherlv_9=(Token)match(input,20,FOLLOW_3); 

                    				newLeafNode(otherlv_9, grammarAccess.getTableAccess().getLeftParenthesisKeyword_6_1());
                    			
                    // InternalDiningRoomTextual.g:357:4: ( ( ruleEString ) )
                    // InternalDiningRoomTextual.g:358:5: ( ruleEString )
                    {
                    // InternalDiningRoomTextual.g:358:5: ( ruleEString )
                    // InternalDiningRoomTextual.g:359:6: ruleEString
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTableRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getTableAccess().getAroundChairCrossReference_6_2_0());
                    					
                    pushFollow(FOLLOW_14);
                    ruleEString();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalDiningRoomTextual.g:373:4: (otherlv_11= ',' ( ( ruleEString ) ) )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==14) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // InternalDiningRoomTextual.g:374:5: otherlv_11= ',' ( ( ruleEString ) )
                    	    {
                    	    otherlv_11=(Token)match(input,14,FOLLOW_3); 

                    	    					newLeafNode(otherlv_11, grammarAccess.getTableAccess().getCommaKeyword_6_3_0());
                    	    				
                    	    // InternalDiningRoomTextual.g:378:5: ( ( ruleEString ) )
                    	    // InternalDiningRoomTextual.g:379:6: ( ruleEString )
                    	    {
                    	    // InternalDiningRoomTextual.g:379:6: ( ruleEString )
                    	    // InternalDiningRoomTextual.g:380:7: ruleEString
                    	    {

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getTableRule());
                    	    							}
                    	    						

                    	    							newCompositeNode(grammarAccess.getTableAccess().getAroundChairCrossReference_6_3_1_0());
                    	    						
                    	    pushFollow(FOLLOW_14);
                    	    ruleEString();

                    	    state._fsp--;


                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    otherlv_13=(Token)match(input,21,FOLLOW_8); 

                    				newLeafNode(otherlv_13, grammarAccess.getTableAccess().getRightParenthesisKeyword_6_4());
                    			

                    }
                    break;

            }

            otherlv_14=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_14, grammarAccess.getTableAccess().getRightCurlyBracketKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTable"


    // $ANTLR start "entryRuleChair"
    // InternalDiningRoomTextual.g:408:1: entryRuleChair returns [EObject current=null] : iv_ruleChair= ruleChair EOF ;
    public final EObject entryRuleChair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleChair = null;


        try {
            // InternalDiningRoomTextual.g:408:46: (iv_ruleChair= ruleChair EOF )
            // InternalDiningRoomTextual.g:409:2: iv_ruleChair= ruleChair EOF
            {
             newCompositeNode(grammarAccess.getChairRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleChair=ruleChair();

            state._fsp--;

             current =iv_ruleChair; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleChair"


    // $ANTLR start "ruleChair"
    // InternalDiningRoomTextual.g:415:1: ruleChair returns [EObject current=null] : ( () otherlv_1= 'Chair' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'order' ( (lv_order_5_0= ruleEInt ) ) )? otherlv_6= '}' ) ;
    public final EObject ruleChair() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        AntlrDatatypeRuleToken lv_order_5_0 = null;



        	enterRule();

        try {
            // InternalDiningRoomTextual.g:421:2: ( ( () otherlv_1= 'Chair' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'order' ( (lv_order_5_0= ruleEInt ) ) )? otherlv_6= '}' ) )
            // InternalDiningRoomTextual.g:422:2: ( () otherlv_1= 'Chair' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'order' ( (lv_order_5_0= ruleEInt ) ) )? otherlv_6= '}' )
            {
            // InternalDiningRoomTextual.g:422:2: ( () otherlv_1= 'Chair' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'order' ( (lv_order_5_0= ruleEInt ) ) )? otherlv_6= '}' )
            // InternalDiningRoomTextual.g:423:3: () otherlv_1= 'Chair' ( (lv_name_2_0= ruleEString ) ) otherlv_3= '{' (otherlv_4= 'order' ( (lv_order_5_0= ruleEInt ) ) )? otherlv_6= '}'
            {
            // InternalDiningRoomTextual.g:423:3: ()
            // InternalDiningRoomTextual.g:424:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getChairAccess().getChairAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,22,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getChairAccess().getChairKeyword_1());
            		
            // InternalDiningRoomTextual.g:434:3: ( (lv_name_2_0= ruleEString ) )
            // InternalDiningRoomTextual.g:435:4: (lv_name_2_0= ruleEString )
            {
            // InternalDiningRoomTextual.g:435:4: (lv_name_2_0= ruleEString )
            // InternalDiningRoomTextual.g:436:5: lv_name_2_0= ruleEString
            {

            					newCompositeNode(grammarAccess.getChairAccess().getNameEStringParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_4);
            lv_name_2_0=ruleEString();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getChairRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_2_0,
            						"geodes.sms.diningroom.DiningRoomTextual.EString");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,12,FOLLOW_15); 

            			newLeafNode(otherlv_3, grammarAccess.getChairAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalDiningRoomTextual.g:457:3: (otherlv_4= 'order' ( (lv_order_5_0= ruleEInt ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==23) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalDiningRoomTextual.g:458:4: otherlv_4= 'order' ( (lv_order_5_0= ruleEInt ) )
                    {
                    otherlv_4=(Token)match(input,23,FOLLOW_10); 

                    				newLeafNode(otherlv_4, grammarAccess.getChairAccess().getOrderKeyword_4_0());
                    			
                    // InternalDiningRoomTextual.g:462:4: ( (lv_order_5_0= ruleEInt ) )
                    // InternalDiningRoomTextual.g:463:5: (lv_order_5_0= ruleEInt )
                    {
                    // InternalDiningRoomTextual.g:463:5: (lv_order_5_0= ruleEInt )
                    // InternalDiningRoomTextual.g:464:6: lv_order_5_0= ruleEInt
                    {

                    						newCompositeNode(grammarAccess.getChairAccess().getOrderEIntParserRuleCall_4_1_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_order_5_0=ruleEInt();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getChairRule());
                    						}
                    						set(
                    							current,
                    							"order",
                    							lv_order_5_0,
                    							"geodes.sms.diningroom.DiningRoomTextual.EInt");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getChairAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleChair"


    // $ANTLR start "entryRuleEInt"
    // InternalDiningRoomTextual.g:490:1: entryRuleEInt returns [String current=null] : iv_ruleEInt= ruleEInt EOF ;
    public final String entryRuleEInt() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEInt = null;


        try {
            // InternalDiningRoomTextual.g:490:44: (iv_ruleEInt= ruleEInt EOF )
            // InternalDiningRoomTextual.g:491:2: iv_ruleEInt= ruleEInt EOF
            {
             newCompositeNode(grammarAccess.getEIntRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEInt=ruleEInt();

            state._fsp--;

             current =iv_ruleEInt.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEInt"


    // $ANTLR start "ruleEInt"
    // InternalDiningRoomTextual.g:497:1: ruleEInt returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleEInt() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;


        	enterRule();

        try {
            // InternalDiningRoomTextual.g:503:2: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // InternalDiningRoomTextual.g:504:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // InternalDiningRoomTextual.g:504:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            // InternalDiningRoomTextual.g:505:3: (kw= '-' )? this_INT_1= RULE_INT
            {
            // InternalDiningRoomTextual.g:505:3: (kw= '-' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==24) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalDiningRoomTextual.g:506:4: kw= '-'
                    {
                    kw=(Token)match(input,24,FOLLOW_16); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getEIntAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_2); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getEIntAccess().getINTTerminalRuleCall_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEInt"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x000000000000A000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000410000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00000000000E8000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000001000040L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x00000000000C8000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000088000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000204000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000808000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000040L});

}