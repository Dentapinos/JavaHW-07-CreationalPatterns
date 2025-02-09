
# Hi, I'm Dentapinos! 👋


# Домашнее задание №7 - порождающие Паттерны
Трасса трофи-рейда представляется в виде последовательности контрольных пунктов КП.<br>
Бывают два вида КП: обязательного и необязательного взятия.<br>
Про каждое КП хранится следующая информация: имя кп (строка); координаты: широта (число с плавающей точкой в диапазоне -90.0°…+90°) и долгота (число с плавающей точкой в диапазоне -180°…+180°).<br>
Для КП с необязательным взятием также хранится значение штрафа за пропуск этого кп (число с плавающей точкой, представляющее время в часах).<br>
Также участникам рейда представляется заранее определенное количество автомобилей, каждый из которых имеет госномер, цвет, мощность и запас топлива.
При этом на одной трассе соревнуются грузовики и Легковые машины, для которых на одних и тех же чек-поинтах есть места для стоянки и для ремонта


## Что я сделал
В задании я выделил главные обьекты: Area - интерфейс площадки для ремонта или остановки; 
Car - интерфейс машины, CarFactory - интерфейс создания машин. Checkpoint - контрольная точка которая содержит в себе массив ремонтных и остановочных площадок,
а массив площадок только потому что, по заданию каждая парковка или ремонтная площадка подходит только определенному виду машин.
Еще я создал Track, его главная задача собрать из контрольных точек определенный маршрут.

Prototype паттерн - я применил в абстрактном классе [SimpleCar.java](src/main/java/org/example/cars/SimpleCar.java) переопределив метод для клонирования машины - clone().
Factory method паттерн я применил для создания конкретного типа площадки - парковки или ремонта [AreaCreator.java](src/main/java/org/example/areas/AreaCreator.java) 
и [RepairCreator](src/main/java/org/example/areas/RepairCreator.java) конкретных ремонтных площадок. 
Simple Factory(Простую фабрику) я использовал в классах [ParkingFactory.java](src/main/java/org/example/areas/area/ParkingFactory.java) и
[RepairFactory.java](src/main/java/org/example/areas/area/RepairFactory.java) создания парковки и ремонтной площадки для определенного вида машины.
Используя при этом семейство связанных объектов [ParkingAreaForPassengerCar.java](src/main/java/org/example/areas/parking/ParkingAreaForPassengerCar.java)
[ParkingAreaForTrack.java](src/main/java/org/example/areas/parking/ParkingAreaForTrack.java) и
[RepairAreaForPassengerCar.java](src/main/java/org/example/areas/repair/RepairAreaForPassengerCar.java)
[RepairAreaForTrack.java](src/main/java/org/example/areas/repair/RepairAreaForTrack.java).
Еще простую фабрику я применил для создания машин [CarFactoryImpl.java](src/main/java/org/example/cars/CarFactoryImpl.java)

Creation method - я использовал в создании контрольных точек в классах [CheckpointMandatory.java](src/main/java/org/example/checkpoints/CheckpointMandatory.java)
[CheckpointOptional.java](src/main/java/org/example/checkpoints/CheckpointOptional.java), создав понятные методы для создания объектов onlyCoordinatesAndName() и
withAreas().
Так же я применил наследование, абстракцию, перегрузку конструктора, переопределение методов.

## Lessons Learned
Познакомился с порождающими паттернами проектирования такими как Создающий метод, Простая фабрика, Абстрактная фабрика,
Абстрактный метод, Прототип.

## Authors

- [@Dentapinos](https://github.com/Dentapinos)


