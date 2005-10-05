package com.jamescookie.scrabble.waf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import com.jamescookie.scrabble.Utils;
import com.jamescookie.scrabble.Tray;
import com.jamescookie.scrabble.Filter;
import com.jamescookie.scrabble.WordProcessing;
import com.jamescookie.scrabble.Operator;
import com.jamescookie.scrabble.WordLoader;

public class ScrabbleAction extends Action {

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String board = request.getParameter("board");
        String letters = request.getParameter("letters");
        String operatorStr = request.getParameter("operator");
        String resultLength = request.getParameter("resultLength");
        String action = request.getParameter("scrabbleAction");

        System.out.println("action = " + action);

        Operator operator = operatorStr == null || operatorStr.length() == 0 ? null : Operator.findOperator(new Integer(operatorStr));
        Integer maxLength = resultLength == null || resultLength.length() == 0 ? null : new Integer(resultLength);
        WordProcessing thread;

        if ("Filter".equals(action)) {
            if (board != null && board.length() > 0) {
                request.setAttribute("words", Utils.sortWords(getFilter(board, maxLength, operator).filter(WordLoader._words)));
            }
        } else {
            if (Utils.isValidText(letters)) {
                Filter f = getFilter(board, maxLength, operator);
                Tray t = new Tray(letters, f);
                thread = new WordProcessing(t, f);
                thread.start();
                thread.join();
                request.setAttribute("words", thread.getWords());
            }
        }

        return actionMapping.findForward("success");
    }


    private Filter getFilter(String board, Integer maxLength, Operator operator) {
        Filter f = new Filter();
        if (maxLength != null) {
            f.setLength(maxLength);
        }
        f.setOperator(operator);
        f.setMustContain(board);
        return f;
    }

}

