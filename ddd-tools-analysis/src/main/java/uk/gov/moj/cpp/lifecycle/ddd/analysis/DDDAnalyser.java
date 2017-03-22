package uk.gov.moj.cpp.lifecycle.ddd.analysis;

import static java.util.stream.Collectors.toList;
import static org.reflections.util.ClasspathHelper.forPackage;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

public class DDDAnalyser {

    private final String basePkg;

    public DDDAnalyser(final String basePkg) {
        this.basePkg = basePkg;
    }

    public void printClassesAnnotatedWith(Class clazz) {
        getClassNamesAnnotatedWith(clazz)
                .forEach(aClass -> System.out.println(aClass));
    }

    public void printClassesWithoutAnnotations(final List<Class> classes) {

        final List<String> annotatedClasses = classes
                .stream()
                .flatMap(aClass -> getClassesAnnotatedWith(aClass))
                .distinct()
                .map(aClass -> aClass.getName())
                .collect(toList());

        final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
                .setUrls(loadUrls())
                .setScanners(new SubTypesScanner(false));

        final Reflections reflections = new Reflections(configurationBuilder);
        final List<String> allTypes = reflections.getAllTypes()
                .stream()
                .distinct()
                .collect(toList());

        allTypes.removeAll(annotatedClasses);

        System.out.println("Types without annotations: ");
        allTypes.forEach(System.out::println);
    }

    private Collection<URL> loadUrls() {
        final Collection<URL> urls = forPackage(basePkg);
        return urls;
    }

    private Stream<Class<?>> getClassesAnnotatedWith(final Class clazz) {
        final Reflections reflections = new Reflections(basePkg);
        return reflections.getTypesAnnotatedWith(clazz).stream();
    }

    private Stream<String> getClassNamesAnnotatedWith(final Class clazz) {
        return getClassesAnnotatedWith(clazz)
                .map(aClass -> aClass.getName())
                .sorted();
    }
}
