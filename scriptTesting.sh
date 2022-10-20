for i in {1..20}
do
    echo "SEED: " $i
    start=`date +%s%N`
    #Replace all from /usr/lib to /AIMA.jar with the command that appears at the run option at the toolbar of Eclipse
    C:\Users\heche\.jdks\semeru-11.0.17\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2022.2.2\lib\idea_rt.jar=62790:C:\Program Files\JetBrains\IntelliJ IDEA 2022.2.2\bin" -Dfile.encoding=UTF-8 -classpath C:\Users\heche\IdeaProjects\IA\out\production\IA_semana3;C:\Users\heche\IdeaProjects\IA\PracticaBusquedaLocal\AIMA.jar;C:\Users\heche\IdeaProjects\IA\PracticaBusquedaLocal\out\production\AIMAexample-master\IA\ProbIA5\AIMA.jar AIMAMain
    end=`date +%s%N`
    echo "$(($(($end-$start))/1000000)) ms"
    sleep 3 #Spacing the executions so that one doesn't get affected by another.
done
