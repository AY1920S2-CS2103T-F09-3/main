package igrad.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import igrad.commons.core.index.Index;
import igrad.commons.util.StringUtil;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.avatar.Avatar;
import igrad.model.module.Title;
import igrad.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String specifier} into a {@code Specifier}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code specifier} is invalid.
     */
    public static Specifier parseSpecifier(String specifier) throws ParseException {
        requireNonNull(specifier);

        String trimmedSpecifier = specifier.trim();
        if (!Title.isValidTitle(trimmedSpecifier)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Specifier(trimmedSpecifier);
    }

    /**
     * Parses a {@code String name} into an {@code Avatar}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code avatarName} is invalid.
     */
    public static Avatar parseAvatarName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Avatar.isValidName(trimmedName)) {
            throw new ParseException(Avatar.MESSAGE_CONSTRAINTS);
        }
        return new Avatar(trimmedName);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTag(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagsSet = new HashSet<>();
        for (String tagName : tags) {
            tagsSet.add(parseTag(tagName));
        }
        return tagsSet;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
