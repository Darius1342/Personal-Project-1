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

def scrapeEmag():
    cod_emag = requests.get('https://www.emag.ro/tastaturi/c?ref=hp_menu_quick-nav_23_23&type=category').text
    html_code = BeautifulSoup(cod_emag,'lxml');
    data_storage = []

    for anunt in html_code.findAll(class_="card-v2-info"):
        try:
            items = {}
            link = anunt.find('a',class_="card-v2-title")['href']
            title = anunt.find('a',class_="card-v2-title").text
            request_price = requests.get(link).text
            html_price = BeautifulSoup(request_price,'lxml')
            price = html_price.find('p',class_="product-new-price").text

            package = announcement(title,price,link)
            items["Title"] = package.title
            items["Price"] = package.price
            items["Link"] = package.link
            print(items)
            data_storage.append(items)

        except:
            pass
    print("\n----------------------------------------------")

    with open('scrapedEmag.json','w') as output:
        json.dump(data_storage, output)