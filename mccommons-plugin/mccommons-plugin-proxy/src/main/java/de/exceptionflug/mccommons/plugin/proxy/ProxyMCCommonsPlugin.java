package de.exceptionflug.mccommons.plugin.proxy;

import de.exceptionflug.mccommons.config.proxy.ProxyConfig;
import de.exceptionflug.mccommons.config.proxy.ProxyConfigProxyYamlConfigWrapper;
import de.exceptionflug.mccommons.config.shared.ConfigFactory;
import de.exceptionflug.mccommons.config.shared.ConfigItemStack;
import de.exceptionflug.mccommons.core.Converters;
import de.exceptionflug.mccommons.core.Providers;
import de.exceptionflug.mccommons.inventories.api.InventoryBuilder;
import de.exceptionflug.mccommons.inventories.api.item.ItemStackWrapper;
import de.exceptionflug.mccommons.inventories.proxy.builder.ProtocolizeInventoryBuilder;
import de.exceptionflug.mccommons.inventories.proxy.converters.ItemTypeConverter;
import de.exceptionflug.mccommons.inventories.proxy.converters.ProtocolizeClickTypeConverter;
import de.exceptionflug.mccommons.inventories.proxy.converters.ProtocolizeInventoryTypeConverter;
import de.exceptionflug.mccommons.inventories.proxy.converters.ProtocolizeItemTypeConverter;
import de.exceptionflug.mccommons.inventories.proxy.item.ProtocolizeItemStackWrapper;
import de.exceptionflug.mccommons.inventories.proxy.listener.InventoryListener;
import de.exceptionflug.mccommons.plugin.proxy.commands.ConfigReloadCommand;
import de.exceptionflug.mccommons.plugin.proxy.converter.ItemStackConverter;
import de.exceptionflug.protocolize.api.ClickType;
import de.exceptionflug.protocolize.inventory.InventoryType;
import de.exceptionflug.protocolize.items.ItemStack;
import de.exceptionflug.protocolize.items.ItemType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class ProxyMCCommonsPlugin extends Plugin {

    @Override
    public void onEnable() {
        Providers.register(Plugin.class, this);
        Providers.register(InventoryBuilder.class, new ProtocolizeInventoryBuilder());
        ConfigFactory.register(ProxyConfig.class, ProxyConfigProxyYamlConfigWrapper::new);
        Converters.register(ConfigItemStack.class, ItemStack.class, new ItemStackConverter());
        Converters.register(InventoryType.class, de.exceptionflug.mccommons.inventories.api.InventoryType.class, new ProtocolizeInventoryTypeConverter());
        Converters.register(ItemType.class, de.exceptionflug.mccommons.inventories.api.item.ItemType.class, new ProtocolizeItemTypeConverter());
        Converters.register(de.exceptionflug.mccommons.inventories.api.item.ItemType.class, ItemType.class, new ItemTypeConverter());
        Converters.register(ItemStack.class, ItemStackWrapper.class, src -> new ProtocolizeItemStackWrapper((ItemStack) src));
        Converters.register(ItemStackWrapper.class, ItemStack.class, src -> ((ItemStackWrapper)src).getHandle());
        Converters.register(ProtocolizeItemStackWrapper.class, ItemStack.class, src -> ((ItemStackWrapper)src).getHandle());
        Converters.register(ClickType.class, de.exceptionflug.mccommons.inventories.api.ClickType.class, new ProtocolizeClickTypeConverter());

        ProxyServer.getInstance().getPluginManager().registerListener(this, new InventoryListener());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ConfigReloadCommand());
    }

}
