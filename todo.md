# MCBDisenchant Plugin – ToDo

## ⚙️ Project Setup
- [ ] Create a new Maven project
- [ ] Set Minecraft version to 1.21.4
- [ ] Set up `plugin.yml`:
  - [ ] Name: `MCBDisenchant`
  - [ ] Main class path
  - [ ] Version
  - [ ] Command: `/disenchant`
  - [ ] Permission: `mcbdisenchant.use`

## 🧱 GUI Design
- [ ] Create 45-slot inventory GUI
- [ ] Filler slots:
  - [ ] Configurable via `config.yml`
  - [ ] Default filler: gray stained glass pane
  - [ ] Fill all slots except: 12-16, 19, 21-25, 30-34
- [ ] Slot 19 shows the item in the player’s main hand
- [ ] Other empty slots:
  - [ ] One enchanted book per enchantment on the item
  - [ ] Book name and lore use MiniMessage
  - [ ] Add lore to enchanted book showing enchantment level

## 🛠️ Core Functionality
- [ ] `/disenchant` command:
  - [ ] Opens disenchant GUI
  - [ ] Checks for item in main hand
  - [ ] If no item or no enchantments, show MiniMessage-based error
- [ ] On enchanted book click:
  - [ ] Remove selected enchantment from item
  - [ ] Return item to player (with modified enchantments)
  - [ ] Close inventory
  - [ ] Send confirmation using MiniMessage

## 🧾 Config System
- [ ] Create `config.yml` with:
  - [ ] Filler item material (e.g. `GRAY_STAINED_GLASS_PANE`)
  - [ ] Filler name (MiniMessage string)
  - [ ] MiniMessage strings for:
    - [ ] GUI title
    - [ ] No item in hand
    - [ ] No enchantments
    - [ ] Enchantment removed
- [ ] Support config reload on plugin reload

## 🧠 Utility Features
- [ ] Integrate Adventure API for MiniMessage support
- [ ] Optional: Add sound or particle when an enchant is removed
- [ ] Optional: Add confirmation GUI or click-to-confirm logic

---

## 🔄 GitHub & CI/CD
- [ ] Push the plugin to a public GitHub repository
- [ ] Set up `.gitignore` for Java/Maven
- [ ] Create GitHub Actions workflow to:
  - [ ] Build the plugin with Maven
  - [ ] Upload the compiled JAR to the GitHub Releases section automatically on tag push
- [ ] Include `README.md` with usage instructions and permissions

---

## 💡 Suggestions
- [ ] Add optional XP cost to remove enchantment
- [ ] Support shift-click to remove all enchantments
- [ ] Add debug log mode
- [ ] Permission node for each enchantment type (optional)
