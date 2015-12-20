#Migrating to yggdrasil 1.5;
#Converting StatisticMeta to StatPointer;
ALTER TABLE `${PREFIX}_statistic` ADD  `classifiers` TEXT NULL DEFAULT NULL;
ALTER TABLE `${PREFIX}_statistic` DROP INDEX statistic;
ALTER TABLE `${PREFIX}_statistic` ADD UNIQUE  `stat_uniuq` (  `statistic` ,  `classifiers` ( 512 ) );
