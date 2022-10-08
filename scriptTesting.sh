

for i in {1..1000}
do
    echo "SEED: " $i
    #Replace all from /usr/lib to /AIMA.jar with the command that appears at the run option at the toolbar of Eclipse
    time /usr/lib/jvm/java-1.11.0-openjdk-amd64/bin/java -javaagent:/home/david/Desktop/idea-IC-222.4167.29/lib/idea_rt.jar=40897:/home/david/Desktop/idea-IC-222.4167.29/bin -Dfile.encoding=UTF-8 -classpath /home/david/IdeaProjects/IA_semana3/out/production/IA_semana3:/home/david/IdeaProjects/IA_semana3/PracticaBusquedaLocal/AIMA.jar:/home/david/IdeaProjects/IA_semana3/PracticaBusquedaLocal/out/production/AIMAexample-master/IA/ProbIA5/AIMA.jar -Xmx5800m -Xms5800m AIMAMain $i | tail -5

    sleep 10 #Spacing the executions so that one doesn't get affected by another.
done
