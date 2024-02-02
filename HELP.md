# Disable Services v.1.0.0

## Reference Documentation

For further reference, please consider the following sections:

* [Csv Guide](https://www.bezkoder.com/spring-boot-upload-csv-file/)
* [Spring profiles](https://salithachathuranga94.medium.com/spring-boot-profiles-6f8b841cf11c)
* [Hikari Configuration](https://github.com/brettwooldridge/HikariCP#configuration-knobs-baby)

## Disable status

|       | **DSTATUS** | **DRELEASESTATE** |
| :---: | :---------: | :---------------: |
|   0   |  RELEASED   |         Y         |
|   1   |    DONE     |         N         |
|   2   |      0      |         N         |
|   3   |   EXPIRED   |         N         |
|   4   |   GENWWW    |         N         |
|   5   |      0      |         H         |
|   6   |   DELETED   |         H         |
|   7   |  RELEASED   |         O         |
|   8   |   DELETED   |         Y         |
|   9   |    EDIT     |         E         |

### Compile skipping tests

mvn `-Dmaven.test.skip=true install
