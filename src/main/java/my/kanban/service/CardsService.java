/**
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <https://unlicense.org>
 */
package my.kanban.service;

import my.kanban.domain.Card;
import my.kanban.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class CardsService {
    @Autowired
    private CardsRepository repository;

    public List<Card> findAll() {
        return repository.findAll();
    }

    public Card create(@Valid Card card) {
        card.setId(null);
        return repository.save(card);
    }

    public Card update(@NotNull Long cardId, @Valid Card cardRequest) {
        Card card = repository.findById(cardId)
            .orElseThrow(() -> new EntityNotFoundException(format("Card {0} not found.", cardId)));

        if (isNotBlank(cardRequest.getDescription())) {
            card.setDescription(cardRequest.getDescription());
        }

        if (cardRequest.getColumn() != null) {
            card.setColumn(cardRequest.getColumn());
        }

        return repository.save(card);
    }

    public void delete(@NotNull Long cardId) {
        boolean exists = repository.existsById(cardId);
        if (!exists) {
            new EntityNotFoundException(format("Card {0} not found.", cardId));
        }

        repository.deleteById(cardId);
    }
}
