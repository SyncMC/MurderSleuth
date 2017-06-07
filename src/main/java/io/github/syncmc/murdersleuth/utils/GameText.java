package io.github.syncmc.murdersleuth.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public enum GameText implements ITextComponent
{
    GAME_START("YELLOW, RED, YELLOW", "The game starts in ", "1", " second!"),
    
    KNIFE_NAME("GREEN", "Knife"),
    KNIFE_LORE_ONE("GRAY", "Use your Knife to kill"),
    KNIFE_LORE_TWO("GRAY", "players."),
    
    BOW_NAME("GREEN", "Bow"),
    DETECTIVE_BOW_LORE_ONE("GRAY", "Use your Bow to kill the"),
    DETECTIVE_BOW_LORE_TWO("GRAY", "murderer."),
    INNOCENT_BOW_LORE_ONE("GRAY", "Use this bow to defend"),
    INNOCENT_BOW_LORE_TWO("GRAY", "yourself from the Murderer!"),
    
    NO_MURDERER("GRAY", "No murderer found."),
    NO_DETECTIVE("GRAY", "No detective found."),
    NO_INNOCENTS_WITH_BOWS("GRAY", "No innocent players with bows found."),
    
    WEAPON_HOLDERS_CLEARED("GRAY", "All weapon holders have been cleared."),
    
    PLAYER_VIEW_ALL("GRAY", "[1/7] All players are now shown."),
    PLAYER_VIEW_ALIVE("GRAY", "[2/7] Only alive players are now shown."),
    PLAYER_VIEW_INNOCENTS("GRAY", "[3/7] Only innocent players are now shown."),
    PLAYER_VIEW_HOLDING_BOWS("GRAY", "[4/7] Only players holding bows are now shown."),
    PLAYER_VIEW_INNOCENTS_HOLDING_BOWS("GRAY", "[5/7] Only innocent players holding bows are now shown."),
    PLAYER_VIEW_DETECTIVE("GRAY", "[6/7] Only the detective is now shown."),
    PLAYER_VIEW_MURDERER("GRAY", "[7/7] Only the murderer is now shown."),
    
    GOLD_VIEW_ALL("GRAY", "[1/2] Gold ingots are now shown."),
    GOLD_VIEW_NONE("GRAY", "[2/2] Gold ingots are no longer shown.");
    
    private final ITextComponent component;
    private GameText(String commaStyleStrings, String... messages)
    {
        this.component = createITextComponent(commaStyleStrings, messages);
    }
    
    public static ITextComponent createITextComponent(String commaStyleStrings, String... messages)
    {
        ITextComponent component = null;
        
        List<String> styleStringsList = Arrays.asList(commaStyleStrings.split(", ", -1));
        for (int i = 0; i < styleStringsList.size(); ++i)
        {
            ITextComponent sibling = new TextComponentString(messages[i]);
            Style style = sibling.getStyle();
            
            if (component != null)
            {
                style.setBold(false);
                style.setItalic(false);
                style.setObfuscated(false);
                style.setStrikethrough(false);
                style.setUnderlined(false);
            }
            
            String styleStrings = styleStringsList.get(i);
            List<String> styleStringList = Arrays.asList(styleStrings.split(" \\+ "));
            
            for (String styleString : styleStringList)
            {
                if (styleString.equals("BOLD"))
                {
                    style.setBold(true);
                }
                else if (styleString.equals("ITALIC"))
                {
                    style.setItalic(true);
                }
                else if (styleString.equals("OBFUSCATED"))
                {
                    style.setObfuscated(true);
                }
                else if (styleString.equals("STRIKETHROUGH"))
                {
                    style.setStrikethrough(true);
                }
                else if (styleString.equals("UNDERLINE"))
                {
                    style.setUnderlined(true);
                }
                else
                {
                    style.setColor(TextFormatting.getValueByName(styleString));
                }
            }
            
            if (component == null)
            {
                component = sibling;
            }
            else
            {
                component.appendSibling(sibling);
            }
        }
        
        return component;
    }
    
    public ITextComponent getITextComponent()
    {
        return this.component;
    }
    
    public String getStrippedFormattedText()
    {
        String formattedText = this.getFormattedText();
        return formattedText.substring(0, formattedText.length() - 2);
    }

    @Override
    public Iterator<ITextComponent> iterator()
    {
        return this.component.iterator();
    }

    @Override
    public ITextComponent setStyle(Style style)
    {
        return this.component.setStyle(style);
    }

    @Override
    public Style getStyle()
    {
        return this.component.getStyle();
    }

    @Override
    public ITextComponent appendText(String text)
    {
        return this.component.appendText(text);
    }

    @Override
    public ITextComponent appendSibling(ITextComponent component)
    {
        return this.component.appendSibling(component);
    }

    @Override
    public String getUnformattedComponentText()
    {
        return this.component.getUnformattedComponentText();
    }

    @Override
    public String getUnformattedText()
    {
        return this.component.getUnformattedText();
    }

    @Override
    public String getFormattedText()
    {
        return this.component.getFormattedText();
    }

    @Override
    public List<ITextComponent> getSiblings()
    {
        return this.component.getSiblings();
    }

    @Override
    public ITextComponent createCopy()
    {
        return this.component.createCopy();
    }
}
