from bs4 import BeautifulSoup
import requests
import json
class announcement:
    def __init__(self,title,price,link):
        self.title=title
        self.price=price
        self.link=link
    def get_link(self):
        return self.link

    def get_title(self):
        return self.title

    def get_price(self):
        return self.price

def scrapeOlx():
    cod_olx = requests.get('https://www.olx.ro/electronice-si-electrocasnice/telefoane-mobile/oradea/q-samsung-s10-plus/?search%5Border%5D=filter_float_price%3Aasc').text
    html_code = BeautifulSoup(cod_olx, 'lxml')
    data_storage = []

    for anunt in html_code.find_all(class_="offer-wrapper"):
        try:
            items = {}
            titlu = anunt.find('td', class_="title-cell").h3.a.strong.text
            pret = anunt.find('p', class_="price").strong.text
            link = anunt.find('td', class_="title-cell").h3.a['href']
            package = announcement(titlu,pret,link)
            items["Title"] = package.title
            items["Price"] = package.price
            items["Link"] = package.link
            print(items)
            data_storage.append(items)

        except Exception:
            pass
    print("\n----------------------------------------------")
    with open('scrapedOlx.json','w') as output:
        json.dump(data_storage, output)

