package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;

import java.util.Properties;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{


   

    public static  String getData(String co) throws IOException {
        String url="https://www.worldometers.info/coronavirus/country/"+co +"/";
        Document doc =Jsoup.connect(url).get();
        Elements elements=doc.select("#maincounter-wrap");
        elements.forEach((e) -> {
            String text = e.select("h1").text();
            String count = e.select(".maincounter-number>span").text();
            System.out.println(text + ":" + count);


        });

        return elements.text();
    }

    public static void main( String[] args ) throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("unboxingBigData", getData("String co"));
        producer.send(producerRecord);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a country");
        String co = sc.nextLine();
        System.out.println(getData(co));
        producer.close();




    }}
