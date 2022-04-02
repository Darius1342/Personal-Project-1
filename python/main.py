
from booksExpressScrape import scrapeBooksExpress
from olxScrape import scrapeOlx
from emagScrape import scrapeEmag


def run():
        scrapeBooksExpress()
        scrapeOlx()
        scrapeEmag()

if __name__ == '__main__':
    run()
