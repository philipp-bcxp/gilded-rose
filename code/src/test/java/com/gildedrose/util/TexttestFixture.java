package com.gildedrose.util;


import com.gildedrose.GildedRose;
import com.gildedrose.Item;

public class TexttestFixture {
    public static void main(String[] args) {
        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }
        String gildedRoseOutput = executeGildedRose(days);

        System.out.println(gildedRoseOutput);
    }



    public static String executeGildedRose(int days) {
        StringBuilder sb = new StringBuilder();
        sb.append("OMGHAI!").append("\n");


        Item[] items = new Item[]{
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Aged Brie", 2, 0), //
            new Item("Elixir of the Mongoose", 5, 7), //
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6)};

        GildedRose app = new GildedRose(items);

        for (int i = 0; i < days; i++) {
            sb.append("-------- day ").append(i).append(" --------").append("\n");
            sb.append("name, sellIn, quality").append("\n");
            for (Item item : items) {
                sb.append(item).append("\n");
            }
            sb.append("\n");
            System.out.println();
            app.updateQuality();
        }

        return sb.toString();
    }


}
