# The Assistant
Libraries aimed to developers with multiple functions that will make you things easier.

[![Java CI with Gradle](https://github.com/r3back/the-assistant/actions/workflows/gradle.yml/badge.svg)](https://github.com/r3back/the-assistant/actions/workflows/gradle.yml)
[![](https://jitpack.io/v/r3back/the-assistant.svg)](https://jitpack.io/#r3back/the-assistant)

## Dependency Usage

### Maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

```xml
<dependency>
    <groupId>com.github.r3back</groupId>
    <artifactId>the-assistant</artifactId>
    <version>LATEST</version>
</dependency>
```

### Gradle

```groovy
repositories {
    maven { 
        url "https://jitpack.io" 
    }
}
```

```groovy
dependencies {
    compileOnly "com.github.r3back:the-assistant:LATEST"
}
```

## Building
The-Assistant uses Gradle to handle dependencies & building.

### Requirements
* Java 8 JDK or newer
* Git

### Compiling from source
```sh
$ git clone https://github.com/r3back/the-assistant.git

$ cd the-assistant/

$ ./gradlew build -x shadowJar
```

You can find the output jars in the `plugin/build/libs` or `build/libs` directories.

## API Usage

```java
/**
* The Assistant API
  */
  public interface TheAssistantAPI {
  /**
    * Retrieves Command Provider
    *
    * @return CommandProvider of {@link AssistantCommand}
      */
      public CommandProvider<AssistantCommand> getCommandProvider();

  /**
    * Retrieves Dependency Resolver
    * @return {@link DependencyResolver}
      */
      public DependencyResolver getDependencyResolver();

  /**
    * Retrieves Addon Service
    *
    * @return {@link AddonsService}
      */
      public AddonsService getAddons();

  /**
    * Retrieves NMS Multi Version handler
    *
    * @return {@link NMS}
      */
      public NMS getNms();


    /**
     * Retrieves Plugin instance
     *
     * @return {@link Plugin} instance
     */
    public Plugin getPlugin();
}
```

## Integrations
This plugin includes integrations with different plugins:

#### Economy:
* [RoyaleEconomy](https://www.spigotmc.org/resources/%E2%9A%A1-royaleeconomy-%E2%9A%A1-1-8-1-16-banks-shops-black-market-custom-economy-talismans-more.81135/)
* [TokenManager](https://github.com/Realizedd/TokenManager)
* [PlayerPoints](https://github.com/Rosewood-Development/PlayerPoints)
* [Vault](https://github.com/MilkBowl/Vault)

#### Schematics:
* [FastAsyncWorldEdit](https://github.com/IntellectualSites/FastAsyncWorldEdit)
* [WorldEdit 7](https://github.com/EngineHub/WorldEdit)
* [WorldEdit 6](https://github.com/EngineHub/WorldEdit)

#### Regions:
* [Residence](https://github.com/Zrips/Residence)
* [UltraRegions](https://github.com/IntellectualSites/FastAsyncWorldEdit)
* [WorldGuard 7](https://github.com/EngineHub/WorldGuard)
* [WorldGuard 6](https://github.com/EngineHub/WorldGuard)

#### Placeholders:
* [PlaceholdersAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)
* [MVdWPlaceholderAPI](https://github.com/Maximvdw/MVdWPlaceholderAPI)

#### More Integrations:
* [Advanced-Slime-World-Manager](https://github.com/Paul19988/Advanced-Slime-World-Manager)
* [MythicMobs](https://www.spigotmc.org/resources/%E2%9A%94-mythicmobs-free-version-%E2%96%BAthe-1-custom-mob-creator%E2%97%84.5702/)
* [MMOItems](https://gitlab.com/phoenix-dvpmt/mmoitems)
* [Citizens](https://github.com/CitizensDev/Citizens2)


## License
This App is licensed under the GNU license. Please see [`LICENSE.txt`](https://github.com/r3back/the-assistant/blob/master/LICENSE.txt) for more info.
