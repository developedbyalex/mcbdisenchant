# MCBDisenchant Plugin

A Minecraft plugin that allows players to remove individual enchantments from items through an intuitive GUI interface.

## Features

- **GUI-based Disenchanting**: Clean, easy-to-use 45-slot inventory interface
- **Individual Enchantment Removal**: Remove specific enchantments while keeping others
- **MiniMessage Support**: Rich text formatting for all messages and GUI elements
- **Configurable**: Customize GUI appearance, messages, and sounds
- **Sound Effects**: Audio feedback for user interactions
- **Permission System**: Control who can use the disenchant feature

## Requirements

- **Minecraft Version**: 1.21.4
- **Server Software**: Paper/Spigot
- **Java Version**: 21+

## Installation

1. Download the latest JAR file from the [Releases](../../releases) page
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. The plugin will generate a `config.yml` file in `plugins/MCBDisenchant/`

## Usage

### Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/disenchant` | Opens the disenchant GUI | `mcbdisenchant.use` |

### Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `mcbdisenchant.use` | Allows using the disenchant command | `true` |

### How to Use

1. Hold an enchanted item in your main hand
2. Run the `/disenchant` command
3. In the GUI that opens:
   - Your item appears in the center slot
   - Each enchantment appears as an enchanted book around it
   - Click on any enchanted book to remove that specific enchantment
4. The enchantment is removed and you receive a confirmation message

## Configuration

The `config.yml` file allows you to customize various aspects of the plugin:

```yaml
# GUI Settings
gui:
  title: "<gradient:#ff6b6b:#4ecdc4>Disenchant Item</gradient>"
  filler:
    material: GRAY_STAINED_GLASS_PANE
    name: "<gray> </gray>"

# Messages (using MiniMessage format)
messages:
  no_item_in_hand: "<red>You must hold an item in your main hand to disenchant!</red>"
  no_enchantments: "<yellow>This item has no enchantments to remove!</yellow>"
  enchantment_removed: "<green>Successfully removed <white>{enchantment}</white> from your item!</green>"
  gui_item_name: "<aqua>Item to Disenchant</aqua>"
  enchanted_book_lore:
    - "<gray>Click to remove this enchantment</gray>"
    - "<yellow>Level: {level}</yellow>"

# Sound Effects (optional)
sounds:
  enchantment_removed: ENTITY_EXPERIENCE_ORB_PICKUP
  gui_open: BLOCK_CHEST_OPEN
  error: ENTITY_VILLAGER_NO
```

### Configuration Options

#### GUI Settings
- `gui.title`: The title of the disenchant GUI (supports MiniMessage)
- `gui.filler.material`: Material for filler items in empty slots
- `gui.filler.name`: Display name for filler items

#### Messages
All messages support [MiniMessage](https://docs.advntr.dev/minimessage/format.html) formatting:
- `messages.no_item_in_hand`: Shown when player has no item in main hand
- `messages.no_enchantments`: Shown when item has no enchantments
- `messages.enchantment_removed`: Confirmation message (use `{enchantment}` placeholder)
- `messages.gui_item_name`: Display name for the item in the GUI
- `messages.enchanted_book_lore`: Lore for enchanted books (use `{level}` placeholder)

#### Sound Effects
- `sounds.enchantment_removed`: Sound played when enchantment is removed
- `sounds.gui_open`: Sound played when GUI opens
- `sounds.error`: Sound played for error messages

## Building from Source

1. Clone this repository
2. Ensure you have Java 21+ and Maven installed
3. Run `mvn clean package`
4. The compiled JAR will be in the `target/` directory

## Development

### Project Structure
```
src/main/java/com/mcbdisenchant/
├── MCBDisenchant.java          # Main plugin class
├── commands/
│   └── DisenchantCommand.java  # Command handler
├── gui/
│   └── DisenchantGUI.java      # GUI creation and management
└── listeners/
    └── DisenchantGUIListener.java # GUI interaction handling
```

### Building and Releasing

This project uses GitHub Actions for automated building and releasing:

1. Push your changes to the repository
2. Create a new tag: `git tag v1.0.0`
3. Push the tag: `git push origin v1.0.0`
4. GitHub Actions will automatically build and create a release

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source. Feel free to use, modify, and distribute as needed.

## Support

If you encounter any issues or have suggestions, please open an issue on the [GitHub repository](../../issues).

---

**Note**: This plugin is designed for Minecraft 1.21.4. Compatibility with other versions is not guaranteed. 