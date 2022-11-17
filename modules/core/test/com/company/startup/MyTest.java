package com.company.startup;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyTest {


    private static Wallet wallet = new Wallet();

    private static BigDecimal fee = BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(0.001));
    private static BigDecimal sellVolatility = BigDecimal.valueOf(0.03);
    private static BigDecimal buyVolatility = BigDecimal.valueOf(0.03);

    private static final String SELL = "sell";
    private static final String BUY = "buy";

    public static void main(String[] args) {


    }

    private static void sell(BigDecimal currentPrice) {
        int compare = currentPrice.compareTo(wallet.buyingPrice);
        if (compare > 0) {
            BigDecimal priceChange = currentPrice
                    .divide(wallet.buyingPrice, 3, RoundingMode.CEILING)
                    .multiply(BigDecimal.valueOf(compare))
                    .subtract(BigDecimal.valueOf(1))
                    .abs();

            compare = priceChange.compareTo(sellVolatility);
            if (compare > 0) {

                BigDecimal amount = currentPrice.multiply(wallet.getNEAR());
                BigDecimal totalAmount = amount.multiply(fee);

                wallet.setUSDT(totalAmount);
                wallet.setNEAR(BigDecimal.ZERO);
                wallet.setAction(BUY);
                wallet.setSellingPrice(currentPrice);
                wallet.setBuyingPrice(BigDecimal.ZERO);
                System.out.println(SELL + " " + currentPrice);
            }
        }
    }

    private static void buy(BigDecimal currentPrice) {
        int compare = currentPrice.compareTo(wallet.sellingPrice);
        if (compare < 0) {
            BigDecimal priceChange = currentPrice
                    .divide(wallet.sellingPrice, 3, RoundingMode.CEILING)
                    .multiply(BigDecimal.valueOf(compare))
                    .subtract(BigDecimal.valueOf(1))
                    .abs();

            compare = priceChange.compareTo(buyVolatility);
            if (compare > 0) {

                BigDecimal amount = wallet.getUSDT().divide(currentPrice, 3, RoundingMode.CEILING);
                BigDecimal totalAmount = amount.multiply(fee);

                wallet.setUSDT(BigDecimal.ZERO);
                wallet.setNEAR(totalAmount);
                wallet.setAction(SELL);
                wallet.setBuyingPrice(currentPrice);
                wallet.setSellingPrice(BigDecimal.ZERO);
                System.out.println(BUY + " " + currentPrice);
            }
        }
    }


    private static void setUpWallet() {
        wallet.setAction(SELL);
        wallet.setSellingPrice(BigDecimal.ZERO);
        wallet.setBuyingPrice(BigDecimal.valueOf(2.115));
        wallet.setNEAR(BigDecimal.valueOf(100));
        wallet.setUSDT(BigDecimal.ZERO);

    }

    private static JSONArray getJson() {
        JSONArray jsonArray = null;
        try (BufferedReader br = new BufferedReader(new FileReader("file.txt"));) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            jsonArray = new JSONArray(sb.toString());
        } catch (Exception e) {
        }
        return jsonArray;
    }

    private static class Wallet {
        private String action;
        private BigDecimal USDT;
        private BigDecimal NEAR;
        private BigDecimal buyingPrice;
        private BigDecimal sellingPrice;


        @Override
        public String toString() {
            return "Wallet{" +
                    "action='" + action + '\'' +
                    ", USDT=" + USDT.doubleValue() +
                    ", NEAR=" + NEAR.doubleValue() +
                    ", buyingPrice=" + buyingPrice +
                    ", sellingPrice=" + sellingPrice +
                    '}';
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public BigDecimal getUSDT() {
            return USDT;
        }

        public void setUSDT(BigDecimal USDT) {
            this.USDT = USDT;
        }

        public BigDecimal getNEAR() {
            return NEAR;
        }

        public void setNEAR(BigDecimal NEAR) {
            this.NEAR = NEAR;
        }

        public BigDecimal getBuyingPrice() {
            return buyingPrice;
        }

        public void setBuyingPrice(BigDecimal buyingPrice) {
            this.buyingPrice = buyingPrice;
        }

        public BigDecimal getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(BigDecimal sellingPrice) {
            this.sellingPrice = sellingPrice;
        }
    }

}
