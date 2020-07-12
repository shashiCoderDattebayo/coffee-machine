# coffee-machine
## Assumptions:
1. There exists n outlets which are taken from sampleData.json
2. Outlet is assigned to used user dynamically based on availability. Hence multiple beverages can be processed parallelly until outlets are free.
3. The main option selector and message display are into stdout here. Due to parallelism there messages may be intermingled do not worry.
4. Ingredient is considered low when <= 300.
## Usage:
```
git clone https://github.com/shashiCoderDattebayo/coffee-machine.git
cd coffee-machine
./gradlew build
./gradlew customFatJar
java -jar build/libs/all-in-one-jar.jar sampleData.json
```

## Debug Logs:
```
cd coffee-machine
tail -f logs/log.out
```