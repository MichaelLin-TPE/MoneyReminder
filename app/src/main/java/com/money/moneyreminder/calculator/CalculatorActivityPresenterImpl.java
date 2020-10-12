package com.money.moneyreminder.calculator;

import com.money.moneyreminder.tool.DataProvider;

import java.util.ArrayList;

import static com.money.moneyreminder.calculator.MathAdapter.BACK;
import static com.money.moneyreminder.calculator.MathAdapter.DIVIDE;
import static com.money.moneyreminder.calculator.MathAdapter.LESS;
import static com.money.moneyreminder.calculator.MathAdapter.MULTIPLY;
import static com.money.moneyreminder.calculator.MathAdapter.PLUS;

/**
 *
 * 此頁面應該還可以在優化
 */

public class CalculatorActivityPresenterImpl implements CalculatorActivityPresenter {

    private CalculatorActivityVu mView;

    private static final String OK = "OK";

    private static final String CLEAR_VIEW = "C";

    private static final String POINT = ".";

    private static final String ZERO =  "0";

    private static final String EQUALS = "=";

    private boolean isCalculating = false,isIncome;

    private int firstNumber,secondNumber , totalNumber , calculatorRepeatIndex;

    private String calculateType;

    public CalculatorActivityPresenterImpl(CalculatorActivityVu mView) {
        this.mView = mView;
    }

    @Override
    public void onActivityCreate() {
        mView.showRecyclerView(DataProvider.getInstance().getNumberArray(),DataProvider.getInstance().getMathArray());
    }

    /**
     * 按下數字格式的方法
     * @param number
     */
    @Override
    public void onNumberItemClickListener(String number) {
        switch (number){
            case CLEAR_VIEW:
                mView.resetNumberList();
                mView.clearView();
                break;
            case POINT:
                if (mView.getTvContent().contains(POINT)){
                    return;
                }
                if (mView.getTvContent().equals(ZERO)){
                    mView.setCalculatorContent(number);
                    return;
                }
                mView.setCalculatorContent(number);
                break;
            case OK:
                int finalMoney = Integer.parseInt(mView.getTvContent());
                if (finalMoney <= 0){
                    mView.showToast("無法存小於0或等於0的金錢");
                    return;
                }
                mView.intentToSortActivity(mView.getTvContent(),isIncome);
                break;
            case EQUALS:
                if (mView.getTvContent().contains(POINT)){
                    secondNumber = (int)Float.parseFloat(mView.getTvContent());
                }else {
                    secondNumber  = Integer.parseInt(mView.getTvContent());
                }
                totalNumber = getTotalNumber();
                mView.setCalculatorContentEmpty();
                mView.setCalculatorContent(totalNumber+"");
                isCalculating = false;
                mView.resetNumberList();
                calculatorRepeatIndex = 0;
                break;
            default:
                if (mView.getTvContent().equals(ZERO)){
                    mView.setCalculatorContentEmpty();
                    mView.setCalculatorContent(number);
                    return;
                }
                mView.setCalculatorContent(number);
                break;
        }
        if (isCalculating){
            if (number.equals(CLEAR_VIEW)){
                mView.resetNumberList();
                mView.clearView();
                isCalculating = false;
                calculatorRepeatIndex = 0;
                return;
            }
            mView.setCalculatorContentEmpty();
            mView.setCalculatorContent(number);
            isCalculating = false;;
        }
    }

    private int getTotalNumber() {
        switch (calculateType){
            case PLUS:
                return firstNumber + secondNumber;
            case LESS:
                return firstNumber - secondNumber;
            case MULTIPLY:
                return firstNumber * secondNumber;
            default:
                return firstNumber / secondNumber;
        }
    }

    @Override
    public void onBackButtonClickListener() {
        mView.closePage();
    }


    /**
     * 按下計算鈕的方法
     * @param mathItem
     */
    @Override
    public void onMathItemClickListener(String mathItem) {
        if (mathItem.equals(BACK) && !mView.getTvContent().equals(ZERO)){
            String numberContent = mView.getTvContent().substring(0,mView.getTvContent().length()-1);
            if (numberContent.length() == 0){
                mView.setCalculatorContentBack(ZERO);
                return;
            }
            mView.setCalculatorContentBack(numberContent);
            return;
        }


        if (calculatorRepeatIndex > 0){
            if (mView.getTvContent().contains(POINT)){
                secondNumber = (int)Float.parseFloat(mView.getTvContent());
            }else {
                secondNumber  = Integer.parseInt(mView.getTvContent());
            }
            firstNumber = getTotalNumber();
            mView.setCalculatorContentEmpty();
            mView.setCalculatorContent(firstNumber+"");
            isCalculating = true;
            mView.upDateNumberList();
            calculateType = mathItem;
            calculatorRepeatIndex ++;
            return;
        }
        if (mView.getTvContent().contains(POINT)){
            firstNumber = (int)Float.parseFloat(mView.getTvContent());
        }else {
            firstNumber  = Integer.parseInt(mView.getTvContent());
        }
        isCalculating = true;
        mView.upDateNumberList();
        calculateType = mathItem;
        calculatorRepeatIndex ++;
    }

    @Override
    public void onSetIsIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }
}
