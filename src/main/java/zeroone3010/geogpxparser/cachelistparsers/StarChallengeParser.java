package zeroone3010.geogpxparser.cachelistparsers;

import zeroone3010.geogpxparser.Geocache;
import zeroone3010.geogpxparser.Log;
import zeroone3010.geogpxparser.coordinateformatters.CoordinateFormatter;
import zeroone3010.geogpxparser.coordinateformatters.DefaultCoordinateFormatter;
import zeroone3010.geogpxparser.tabular.CellData;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A parser for finding the number of difficulty and terrain stars achieved per
 * day.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class StarChallengeParser implements ICachesToTabularDataParser {

    private static final DateTimeFormatter OUTPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public TableData getTabularInfo(final List<Geocache> caches) {

        final TableData result = new TableData("starsPerDay");
        final TableRow headerRow = new TableRow(true);

        headerRow.addCell(new CellData("date"));
        headerRow.addCell(new CellData("caches"));
        headerRow.addCell(new CellData("Σ D"));
        headerRow.addCell(new CellData("Σ T"));
        headerRow.addCell(new CellData("Σ (D + T)"));
        result.addRow(headerRow);

        final Stream<Summary> summaryStream = caches.stream()
                .map(cache -> new Pair(Utility.findFoundLog(cache), cache))
                .filter(Pair::isFound)
                .map(Summary::new);
        final Map<LocalDate, Optional<Summary>> stats = summaryStream.collect(Collectors.groupingBy(Summary::getDate, Collectors.reducing(Summary::sum)));

        stats.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(entry -> {
                    final Summary summary = entry.getValue().get();
                    final float difficulty = summary.getDifficulty();
                    final float terrain = summary.getTerrain();
                    final TableRow row = new TableRow(false);
                    row.addCell(new CellData(entry.getKey().format(OUTPUT_DATE_TIME_FORMAT)));
                    row.addCell(new CellData(Integer.toString(summary.getAmount())));
                    row.addCell(new CellData(Float.toString(difficulty)));
                    row.addCell(new CellData(Float.toString(terrain)));
                    row.addCell(new CellData(Float.toString(difficulty + terrain)));
                    return row;
                })
                .forEach(row -> result.addRow(row));

        return result;
    }

    private static final class Pair {
        private final Log log;
        private final Geocache cache;

        Pair(final Log log, final Geocache geocache) {
            this.log = log;
            cache = geocache;
        }

        boolean isFound() {
            return Optional.ofNullable(log).map(l -> l.getType().countsAsFind()).orElse(false);
        }

        Log getLog() {
            return log;
        }

        Geocache getCache() {
            return cache;
        }
    }

    private static final class Summary {
        private final LocalDate date;
        private final float difficulty;
        private final float terrain;
        private final int amount;

        Summary(Pair pair) {
            this.date = pair.getLog().getDate().toLocalDate();
            this.difficulty = pair.getCache().getDifficulty();
            this.terrain = pair.getCache().getTerrain();
            this.amount = 1;
        }

        Summary(final LocalDate date, final float difficulty, final float terrain, final int amount) {
            this.date = LocalDate.from(date);
            this.difficulty = difficulty;
            this.terrain = terrain;
            this.amount = amount;
        }

        LocalDate getDate() {
            return date;
        }

        float getDifficulty() {
            return difficulty;
        }

        float getTerrain() {
            return terrain;
        }

        int getAmount() {
            return amount;
        }

        static Summary sum(final Summary a, final Summary b) {
            if (!Objects.equals(a.getDate(), b.getDate())) {
                throw new IllegalArgumentException("Cannot sum arguments of different dates.");
            }
            return new Summary(a.getDate(),
                    a.getDifficulty() + b.getDifficulty(),
                    a.getTerrain() + b.getTerrain(),
                    a.getAmount() + b.getAmount());
        }
    }
}
