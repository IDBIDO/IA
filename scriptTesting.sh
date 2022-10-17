for i in {1..30}
do
    echo "SEED: " $i
    start=`date +%s%N`
    #Replace all from /usr/lib to /AIMA.jar with the command that appears at the run option at the toolbar of Eclipse
    /usr/lib/jvm/java-1.11.0-openjdk-amd64/bin/java -javaagent:/home/david/Desktop/idea-IC-222.4167.29/lib/idea_rt.jar=40897:/home/david/Desktop/idea-IC-222.4167.29/bin -Dfile.encoding=UTF-8 -classpath /home/david/IdeaProjects/IA_semana3/out/production/IA_semana3:/home/david/IdeaProjects/IA_semana3/PracticaBusquedaLocal/AIMA.jar:/home/david/IdeaProjects/IA_semana3/PracticaBusquedaLocal/out/production/AIMAexample-master/IA/ProbIA5/AIMA.jar -Xmx3000m -Xms3000m AIMAMain $i | tail -7
    end=`date +%s%N`
    echo "$(($(($end-$start))/1000000)) ms"
    sleep 3 #Spacing the executions so that one doesn't get affected by another.
done
