package net.tnemc.core.menu.builder;

import net.tnemc.core.menu.Page;
import net.tnemc.core.menu.callbacks.page.PageSlotClickCallback;
import net.tnemc.core.menu.icon.Icon;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class PageBuilder {

  private final ConcurrentHashMap<Integer, Icon> icons = new ConcurrentHashMap<>();

  private Consumer<PageSlotClickCallback> click;

  private int id;

  public static PageBuilder of(final int id) {
    return new PageBuilder(id);
  }

  public PageBuilder(int id) {
    this.id = id;
  }

  public PageBuilder withID(int id) {
    this.id = id;
    return this;
  }

  public PageBuilder click(Consumer<PageSlotClickCallback> callback) {
    this.click = callback;
    return this;
  }

  public PageBuilder withIcon(final Icon icon) {
    this.icons.put(icon.getSlot(), icon);
    return this;
  }

  public Page create() {
    Page page = new Page(id);
    page.setClick(click);
    page.getIcons().putAll(icons);

    return page;
  }
}