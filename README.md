Simple Minecraft mod to spawn players in a dimension other than the Overworld by default.

## Configuration

| Value               | Description                                                                                                             |
|---------------------|-------------------------------------------------------------------------------------------------------------------------|
| startingDimension   | The resource location of the desired starting dimension.                                                                |
| specificCoordinates | Whether players should be spawned at specific coordinates in the dimension.                                             |
| spawnX              | The x coordinate to spawn players at if `specificCoordinates` is true.                                                  |
| spawnY              | The y coordinate to spawn players at if `specificCoordinates` is true.                                                  |
| spawnZ              | The z coordinate to spawn players at if `specificCoordinates` is true.                                                  |
| generatePlatform    | Whether a 3x3 platform should be generated under players if their first spawn is in the air.                            |
| platformBlockState  | The blockstate that `generatePlatform` uses to make the platform. Supports string format e.g. `minecraft:cake[bites=3]` |


## Platform-specific Notes
### Fabric
Changes to the configuration will be reflected immediately. The config is located in the `.minecraft/config/` folder.

### Forge
Changes to the configuration will be reflected immediately. The config is server-sided on Forge, meaning it's located inside the `.minecraft/saves/[WORLD_NAME]/serverconfigs/` folder. You can set a default value by placing a copy of the configuration you'd like to have in the `.minecraft/defaultconfigs/` folder.   