package io.github.syncmc.murdersleuth.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GameText implements ITextComponent
{
    private final ITextComponent textComponent;
    public GameText(String commaStyleStrings, String... messages)
    {
        this.textComponent = GameText.createITextComponent(commaStyleStrings, messages);
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
    
    public ITextComponent getTextComponent()
    {
        return this.textComponent;
    }
    
    public String getStrippedFormattedText()
    {
        String formattedText = this.getFormattedText();
        return formattedText.substring(0, formattedText.length() - 2);
    }
    
    @Override
    public Iterator<ITextComponent> iterator()
    {
        return this.textComponent.iterator();
    }

    @Override
    public ITextComponent setStyle(Style style)
    {
        return this.textComponent.setStyle(style);
    }

    @Override
    public Style getStyle()
    {
        return this.textComponent.getStyle();
    }

    @Override
    public ITextComponent appendText(String text)
    {
        return this.textComponent.appendText(text);
    }

    @Override
    public ITextComponent appendSibling(ITextComponent component)
    {
        return this.textComponent.appendSibling(component);
    }

    @Override
    public String getUnformattedComponentText()
    {
        return this.textComponent.getUnformattedComponentText();
    }

    @Override
    public String getUnformattedText()
    {
        return this.textComponent.getUnformattedText();
    }

    @Override
    public String getFormattedText()
    {
        return this.textComponent.getFormattedText();
    }

    @Override
    public List<ITextComponent> getSiblings()
    {
        return this.textComponent.getSiblings();
    }

    @Override
    public ITextComponent createCopy()
    {
        return this.textComponent.createCopy();
    }
}
