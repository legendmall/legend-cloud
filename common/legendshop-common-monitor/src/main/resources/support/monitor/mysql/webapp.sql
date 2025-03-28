CREATE TABLE druid_webapp
(
    id                      bigint(20) AUTO_INCREMENT NOT NULL,
    domain                  varchar(45) NOT NULL,
    app                     varchar(45) NOT NULL,
    cluster                 varchar(45) NOT NULL,
    host                    varchar(128),
    pid                     int(10) NOT NULL,
    collectTime             datetime    NOT NULL,
    contextPath             varchar(256),
    runningCount            int(10),
    concurrentMax           int(10),
    requestCount            bigint(20),
    sessionCount            bigint(20),
    jdbcFetchRowCount       bigint(20),
    jdbcUpdateCount         bigint(20),
    jdbcExecuteCount        bigint(20),
    jdbcExecuteTimeNano     bigint(20),
    jdbcCommitCount         bigint(20),
    jdbcRollbackCount       bigint(20),
    osMacOSXCount           bigint(20),
    osWindowsCount          bigint(20),
    osLinuxCount            bigint(20),
    osSymbianCount          bigint(20),
    osFreeBSDCount          bigint(20),
    osOpenBSDCount          bigint(20),
    osAndroidCount          bigint(20),
    osWindows98Count        bigint(20),
    osWindowsXPCount        bigint(20),
    osWindows2000Count      bigint(20),
    osWindowsVistaCount     bigint(20),
    osWindows7Count         bigint(20),
    osWindows8Count         bigint(20),
    osAndroid15Count        bigint(20),
    osAndroid16Count        bigint(20),
    osAndroid20Count        bigint(20),
    osAndroid21Count        bigint(20),
    osAndroid22Count        bigint(20),
    osAndroid23Count        bigint(20),
    osAndroid30Count        bigint(20),
    osAndroid31Count        bigint(20),
    osAndroid32Count        bigint(20),
    osAndroid40Count        bigint(20),
    osAndroid41Count        bigint(20),
    osAndroid42Count        bigint(20),
    osAndroid43Count        bigint(20),
    osLinuxUbuntuCount      bigint(20),
    browserIECount          bigint(20),
    browserFirefoxCount     bigint(20),
    browserChromeCount      bigint(20),
    browserSafariCount      bigint(20),
    browserOperaCount       bigint(20),
    browserIE5Count         bigint(20),
    browserIE6Count         bigint(20),
    browserIE7Count         bigint(20),
    browserIE8Count         bigint(20),
    browserIE9Count         bigint(20),
    browserIE10Count        bigint(20),
    browser360SECount       bigint(20),
    deviceAndroidCount      bigint(20),
    deviceIpadCount         bigint(20),
    deviceIphoneCount       bigint(20),
    deviceWindowsPhoneCount bigint(20),
    botCount                bigint(20),
    botBaiduCount           bigint(20),
    botYoudaoCount          bigint(20),
    botGoogleCount          bigint(20),
    botMsnCount             bigint(20),
    botBingCount            bigint(20),
    botSosoCount            bigint(20),
    botSogouCount           bigint(20),
    botYahooCount           bigint(20),
    PRIMARY KEY (id)
);

CREATE
INDEX druid_webapp_index ON druid_webapp (collectTime, domain, app);